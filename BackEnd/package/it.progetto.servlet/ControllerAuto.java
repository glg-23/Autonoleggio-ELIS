package it.progetto.servlet;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.queries.StoredProcedureCall;

import it.progetto.entity.Auto;
import it.progetto.entity.Categoria;
import it.progetto.entity.Cliente;
import it.progetto.entity.Noleggia;
import it.progetto.entity.Notifiche;
import it.progetto.persistence.Database;


/**
 * Servlet implementation class PannelloAuto
 */
@WebServlet(name="auto", urlPatterns = {"/auto"})
public class ControllerAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	Calendar dataAttuale;
	List<Noleggia> noleggi;
	List<Auto> auto;
	HttpSession session;
	Noleggia n;
	Date dataFine=null;
	Date dataInizio=null;
	SimpleDateFormat simple;
	Auto a;
	Cliente cliente;
	private Database db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAuto() {
        super();
        emf=  Persistence.createEntityManagerFactory("autonoleggio");
		em = emf.createEntityManager();
		dataAttuale = Calendar.getInstance();
		this.noleggi=new ArrayList<Noleggia>();
		this.auto = new ArrayList<Auto>();
		n= new Noleggia();
		simple = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		a	= new Auto();
        cliente = new Cliente();
        db = Database.getIstance();
    }
    
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("allauto")!=null) {
			auto = db.getAutoNamedQuery();
			request.setAttribute("allauto", auto);
		}
		
		
		
		session = request.getSession();
		cliente = (Cliente) session.getAttribute("loginCliente");
		
		if(cliente != null && request.getParameter("carrello")!=null) {
			if(session.getAttribute("carrello") == null) {
			
				session.setAttribute("carrello", a);
			}else{
				a = (Auto)session.getAttribute("carrello");
				double totale = (double)session.getAttribute("totaleCarrello");
				
			}
		}
		
		if(request.getParameter("id") != null) {
			
			  a = (Auto) session.getAttribute("carrello");
	          a = db.getAutoById(Integer.parseInt(request.getParameter("id")));
	          
			
			double totale = Double.parseDouble(request.getParameter("totale")); 
	        if(session==null) {
	        	session = request.getSession();
	        }
			session.setAttribute("totaleCarrello", totale);
			
			session.setAttribute("carrello", a);
			
		}
		
		if(request.getParameter("rimuovicarrello")!=null) {
			a = (Auto)session.getAttribute("carrello");
			if(a!=null) {
			a=null;
			}
			session.removeAttribute("carrello");
			session.removeAttribute("totaleCarrello");
			session.removeAttribute("diff");
	        session.removeAttribute("p");
			System.out.println(a);
			response.sendRedirect("index");
		}
		
		if(request.getParameter("noleggio")!=null) {
			Cliente cliente = (Cliente) session.getAttribute("loginCliente");
		    Date dataInizio = (Date) session.getAttribute("datainizio");
		    Date dataFine = (Date) session.getAttribute("datafine");
		    double totale = (double) session.getAttribute("totaleCarrello");
		    int numero = (int)(Math.random()*10000)+1;
		    
			List<Noleggia> allNoleggi = db.cercaNoleggioByNumero(numero);
			if(!allNoleggi.isEmpty()) {
				for(Noleggia n : allNoleggi) {
					if(n.getDataFine().after(dataInizio)) {
						numero = (int)(Math.random()*10000)+1;
						
					}
				}
			}
			
			Cliente client = db.getClienteById(cliente.getId());
			session.setAttribute("datiCliente", cliente);
		    n.setDataInizio(dataInizio);
		    n.setDataFine(dataFine);
		    n.setIdCliente(cliente.getId());
		    n.setIdAuto(Integer.parseInt(request.getParameter("idauto")));
		    n.setNumeroPrenotazione(numero);
		    n.setTotale(totale);
		    db.addNoleggio(n);
		    
		    Notifiche notifica = new Notifiche();
			notifica.setIdCliente(cliente.getId());
			notifica.setTesto("Facci sapere la tua opinione, <br> lascia una recensione su facebook");
			db.addNotifica(notifica);		
		    
		    session.removeAttribute("carrello");
		    session.removeAttribute("datainizio");
		    session.removeAttribute("datafine");
		    session.removeAttribute("noleggiCliente");
            session.removeAttribute("autoCliente");
            session.removeAttribute("totaleCarrello");
            session.removeAttribute("diff");
            session.removeAttribute("p");
            
            noleggi = db.getNoleggiByCliente(cliente.getId());
			session.setAttribute("noleggiCliente", noleggi);
			auto = db.getAllAuto();
			session.setAttribute("autoCliente", auto);
			List<Notifiche> notific = db.getAllNotifiche();
			session.setAttribute("notifica", notific);
			
			
			
			
		    
		}
				
				
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("preventivo")!=null) {
			
			
				
					try {
						//prendo i due parametri inseriti dal cliente/utente e li salvo su un istanza di java.util.date
						dataInizio = (Date) simple.parse(request.getParameter("datainizio")+" "+ request.getParameter("dalle"));
					    dataFine= (Date)simple.parse(request.getParameter("datafine")+" "+ request.getParameter("alle"));	
					    
					    
					    
					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
					
					//calcolo i giorni tra una data e l'altra
					
					long diffInMillies = Math.abs(dataFine.getTime() - dataInizio.getTime());
				    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				    session = request.getSession();
				    session.setAttribute("diff", diff);
					
					//calcolo il prezzo e poi il totale e lo salvo nella request attribute
				
					
					
					
					//salvo la data attuale su un oggetto java.util.Date dopo averlo convertito da string
					
					Date todayDate=null;
					try {
						todayDate = simple.parse(simple.format(dataAttuale.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					int mia = dataFine.compareTo(todayDate);
					System.out.println(mia);
					//valido le date inserite se sono prima della data odierna ritorna alla home altrimenti fa il preventivo
					if(dataInizio.after(todayDate) && dataFine.after(todayDate) && dataInizio.getYear() == todayDate.getYear() && dataFine.after(dataInizio) || dataInizio.compareTo(todayDate)==0 && dataFine.after(todayDate) &&  dataInizio.getYear() == todayDate.getYear() && dataFine.after(dataInizio) ) {
					
					session = request.getSession();
					cliente = (Cliente) session.getAttribute("loginCliente");
					if(cliente!=null) {
				    session.setAttribute("datainizio", dataInizio);	
					session.setAttribute("datafine", dataFine);
					}
					//prendo i noleggi che hanno datainizio = dataInizio inserita dal cliente e datafine = datafine inserita dal cliente	
					String dataI = simple.format(dataInizio);
					String dataF = simple.format(dataFine);
					noleggi = db.getNoleggiByData(dataI, dataF);
					
					
					//seleziono le auto per la categoria scelta nel preventivo
					if(request.getParameter("categoria").equals("all") ) {
					auto = db.getAutoNamedQuery();
				    List<Categoria> prezzo = db.getPrezzoFromCategoria();		    
					request.setAttribute("prezzi", prezzo);
					}
					else {
					auto = db.getAutoByCategoria(Integer.parseInt(request.getParameter("categoria")));
					double prezzo = db.getPrezzoFromCategoria(Integer.parseInt(request.getParameter("categoria")));		    
					double totale = (double) diff * prezzo;
					request.setAttribute("totale", totale);
					}
					
					
					//ciclo le auto e i noleggi se idauto di noleggi e = a id auto 
					//di auto cancello l'elemento che ha quell'id dalla lista di auto e setto un attributo alla lista
						if(!noleggi.isEmpty()) {
						for(Noleggia nole : noleggi) {
						for(int i = 0;i<auto.size();i++) {
							
							if(nole.getIdAuto()==auto.get(i).getId()) {
								
								
								 
								auto.remove(i);
								request.setAttribute("disponibili", auto);
								
								
								
								
							}
						}
					}
						
						
						}
						
						else {
							request.setAttribute("disponibili", auto);
							
						}
					
						
					
					
					
					
					
					
					
			
		}
		}
		
		
	}
	
	
	
	

}
