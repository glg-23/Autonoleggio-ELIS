package it.progetto.servlet;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.progetto.entity.Admin;
import it.progetto.entity.Auto;
import it.progetto.entity.Cliente;
import it.progetto.entity.Noleggia;
import it.progetto.entity.Notifiche;
import it.progetto.entity.Staff;
import it.progetto.persistence.Database;


/**
 * Servlet implementation class Registrazione
 */
@WebServlet(name = "controller" ,urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
	Cliente cliente;
	private List<Cliente> clienti;
	private List<Noleggia> noleggi;
	private List<Auto> auto;
	private List<Staff> staff;
	private Database db;
	private EntityManagerFactory em;
	private EntityManager manager;
	
    /**
     * @return 
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        
       
		cliente= new Cliente();
		clienti=new ArrayList<Cliente>();
		noleggi = new ArrayList<Noleggia>();
		auto = new ArrayList<Auto>();
		staff = new ArrayList<Staff>();
		db = Database.getIstance();
		em =  Persistence.createEntityManagerFactory("autonoleggio");
		manager = em.createEntityManager();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("logout") != null) {
            session.removeAttribute("loginCliente");
            if(session.getAttribute("totaleCarrello")!=null) {
            	session.removeAttribute("totaleCarrello");
            }
            if(session.getAttribute("carello")!=null) {
             session.removeAttribute("carrello");	
            }
            if(session.getAttribute("datiCliente")!=null) {
            	session.removeAttribute("datiCliente");
            }
            if(session.getAttribute("noleggiCliente")!=null) {
            session.removeAttribute("noleggiCliente");	
            }
            if(session.getAttribute("autoCliente")!=null) {
            session.removeAttribute("autoCliente");	
            }
            
			response.sendRedirect("index");
			
		}
		
		
		if(request.getParameter("cliente")!=null) {
			session=request.getSession();
			Cliente client = (Cliente) session.getAttribute("loginCliente");
			cliente = db.getClienteById(Integer.parseInt(request.getParameter("cliente")));
			session.setAttribute("datiCliente", cliente);
			noleggi = db.getNoleggiByCliente(Integer.parseInt(request.getParameter("cliente")));
			session.setAttribute("noleggiCliente", noleggi);
			auto = db.getAllAuto();
			session.setAttribute("autoCliente", auto);
			response.sendRedirect("jsp/anagrafica.jsp");
			
		}
		
		if(request.getParameter("notifica")!=null) {
			if(request.getParameter("cancella")!=null) {
				
				Notifiche n = db.getNotificaById(Integer.parseInt(request.getParameter("cancella")));
				session = request.getSession();
				List<Notifiche> notifica = (List<Notifiche>) session.getAttribute("notifica");
				for(int i=0;i<notifica.size();i++) {
					if(n.getId()==notifica.get(i).getId()) {
						notifica.remove(i);
					}
						
					}
				
				db.removeNotifica(n);
				session.setAttribute("notifica", notifica);	
				response.sendRedirect("index");
				
				
				
			}
		}
		
	
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("lastname")!=null) {
				cliente = new Cliente();
				cliente.setNome(request.getParameter("name"));
				cliente.setCognome(request.getParameter("lastname"));
				cliente.setCitta(request.getParameter("city"));
				cliente.setPatente(request.getParameter("patente"));
				cliente.setEmail(request.getParameter("email"));
				cliente.setPassword(request.getParameter("password"));
				if(!request.getParameter("email").equals("")) {
					db.addCliente(cliente);
				}
				
				
				
				
			} 
		
		else if (request.getParameter("nome")==null) {
                if(request.getParameter("log")!=null) {
                clienti = db.getAllClienteAttivo();
				for (Cliente c : clienti) {
					if(c.getEmail().equals(request.getParameter("email")) && c.getPassword().equals(request.getParameter("password")) && c.getStato()==1) {
						session = request.getSession();
						session.setAttribute("loginCliente", c);
						
						response.sendRedirect("index");
						
					}
				}
                }
				if(request.getParameter("staff")!=null) {
					Admin admin = new Admin();
					staff = db.getStaffNamedQuery() ;
						for (Staff s : staff) {
							if(s.getEmail().equals(request.getParameter("email")) && s.getPassword().equals(request.getParameter("password"))) {
								session = request.getSession();
								session.setAttribute("loginStaff", s);
								
								request.getServletContext().getNamedDispatcher("staff").forward(request, response);
								
							}
							
						}
						
						if(admin.getEmailAdmin().equals(request.getParameter("email")) && admin.getPasswordAdmin().equals(request.getParameter("password"))) {
							session = request.getSession();
							session.setAttribute("loginAdmin", admin);
							
							request.getServletContext().getNamedDispatcher("admin").forward(request, response);
						}
				
				}
			
			}
		
			 if (request.getParameter("modifica")!=null) {
				session = request.getSession();
				Cliente cliente = (Cliente) session.getAttribute("loginCliente");
				cliente.setNome(request.getParameter("name"));
				cliente.setCognome(request.getParameter("cognome"));
				cliente.setCitta(request.getParameter("city"));
				cliente.setPatente(request.getParameter("patente"));
				cliente.setEmail(request.getParameter("email"));
				cliente.setPassword(request.getParameter("password"));
				updateCliente(cliente);
				response.sendRedirect("jsp/anagrafica.jsp");
			}
			
		}
		
	
	
	
	
	private void updateCliente(Cliente cliente) {
	manager.getTransaction().begin(); 
	manager.merge(cliente);
	manager.getTransaction().commit(); 
	
}
	



}




