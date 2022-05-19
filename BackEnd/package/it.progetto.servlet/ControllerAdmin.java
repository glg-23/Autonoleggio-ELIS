package it.progetto.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import it.progetto.entity.Auto;
import it.progetto.entity.Categoria;
import it.progetto.entity.Cliente;
import it.progetto.entity.Noleggia;
import it.progetto.entity.Staff;
import it.progetto.persistence.Database;


@WebServlet(name = "controlleradmin",urlPatterns = {"/controlleradmin"})
public class ControllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession session;    
    Auto a;
	private List<Noleggia> noleggi;
	private List<Auto> auto;
	private List<Cliente> clienti;
	private Database db;
	private EntityManagerFactory em;
	private EntityManager manager;
    
	
	public ControllerAdmin() {
        super();
        a= new Auto();
		noleggi= new ArrayList<Noleggia>();
		auto = new ArrayList<Auto>();
		clienti = new ArrayList<Cliente>();
		db = Database.getIstance();
		em =  Persistence.createEntityManagerFactory("autonoleggio");
		manager = em.createEntityManager();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		if (request.getParameter("logoutadmin") != null) {
            session.removeAttribute("loginAdmin");
            if(session.getAttribute("noleggi")!=null) {
            session.removeAttribute("noleggi");	
            }
            if(session.getAttribute("allauto")!=null) {
                session.removeAttribute("allauto");	
                }
            if(session.getAttribute("allclienti")!=null) {
                session.removeAttribute("allclienti");	
                }
            if(session.getAttribute("approvare")!=null) {
                session.removeAttribute("approvare");	
                }
            if(session.getAttribute("manutenzione")!=null) {
                session.removeAttribute("manutenzione");	
                }
            if(session.getAttribute("autodisponibili")!=null) {
                session.removeAttribute("autodisponibili");	
                }
            if(session.getAttribute("allcategorie")!=null) {
                session.removeAttribute("allcategorie");	
                }
            if(session.getAttribute("trovate")!=null) {
            session.removeAttribute("trovate");
            session.removeAttribute("clienti");
            }
            
			response.sendRedirect("index");
			
		}
		
		
		
		
		if(request.getParameter("noleggi")!=null) {
			noleggi = null;
			auto = null;
			
			if(session.getAttribute("noleggi")!=null) {
				noleggi = (List<Noleggia>) session.getAttribute("noleggi");
			}else {
				noleggi = db.getAllNoleggi();
				session.setAttribute("noleggi", noleggi);
			}
			
			if(session.getAttribute("allauto")==null) {
				auto = db.getAutoNamedQuery();
				session.setAttribute("allauto", auto);
				
				
			}
			
			if(request.getParameter("attivi")!=null) {
				 
				 noleggi = db.getAllNoleggi();
				 session.setAttribute("noleggi", noleggi);
				 
			if(request.getParameter("delete")!=null) {
				noleggi = db.getAllNoleggi();
				for(Noleggia n : noleggi) {
					if(n.getId()==Integer.parseInt(request.getParameter("delete")) ) {
						db.removeNoleggio(n);
					}
				}
				session.removeAttribute("noleggi");
				noleggi = db.getAllNoleggi();
				session.setAttribute("noleggi", noleggi);
				
			
			} 
			
			}
			
			else if (request.getParameter("storico")!=null) {
				noleggi = db.getAllNoleggi();
				session.setAttribute("noleggi", noleggi);
			}
			
			
		}
		
		
		
		
		
		else if (request.getParameter("clienti")!=null) {
			clienti=null;
			List<Cliente> approvare=null;
			if(session.getAttribute("allclienti")!=null && session.getAttribute("approvare")!=null) {
				clienti = (List<Cliente>) session.getAttribute("allclienti");
				approvare = (List<Cliente>)session.getAttribute("approvare");
			}
			
			if(request.getParameter("visualizza")!=null) {
				clienti = db.getAllClienteAttivo();
				session.setAttribute("allclienti", clienti);
			}
			
			if(request.getParameter("approva")!=null) {
				approvare = db.getAllClienteDaApprovare();
				session.setAttribute("approvare", approvare);
				
				if (request.getParameter("approvato")!=null) {
			
			approvare = (List<Cliente>)session.getAttribute("approvare");
			for(Cliente c : approvare) {
				if(c.getId() == Integer.parseInt(request.getParameter("id"))) {
					c.setStato(Integer.parseInt(request.getParameter("stato")));
					updateCliente(c);
					
			session.removeAttribute("approvare");
			session.removeAttribute("allclienti");
			List<Cliente> approvar = db.getAllClienteDaApprovare();
			session.setAttribute("approvare", approvar);
			List<Cliente> client = db.getAllClienteAttivo();
			session.setAttribute("allclienti", client);
		}
				
			}
		}
				
			}
			
		
			if(request.getParameter("cancellacliente")!=null) {
				List<Cliente> clien = db.getAllClienteAttivo();
				for(Cliente c : clien) {
					if(c.getId()==Integer.parseInt(request.getParameter("id"))) {
						db.removeCliente(c);
						
					}
				}
				session.removeAttribute("allclienti");
				List<Cliente> client = db.getAllClienteAttivo();
				session.setAttribute("allclienti", client);
				List<Cliente> approvar = db.getAllClienteDaApprovare();
				session.setAttribute("approvare", approvar);
			}
			
			if(request.getParameter("approvatutti")!=null) {
				approvare = (List<Cliente>)session.getAttribute("approvare");
				for(Cliente c : approvare) {
					    c.setStato(1);
						updateCliente(c);
						
				session.removeAttribute("approvare");
				session.removeAttribute("allclienti");
				List<Cliente> approvar = db.getAllClienteDaApprovare();
				session.setAttribute("approvare", approvar);
				List<Cliente> client = db.getAllClienteAttivo();
				session.setAttribute("allclienti", client);
			
					
				}
			}
			
		
		
		}
		
		 
		
		
		else if(request.getParameter("allstaff")!=null) {
			
			List<Staff> staff= db.getAllStaff();
			session.setAttribute("staff", staff);
		
		if (request.getParameter("cancellastaff")!=null) {
			staff = db.getAllStaff();
			for(Staff s : staff) {
				if(s.getId()==Integer.parseInt(request.getParameter("id"))) {
					db.removeStaff(s);
					
				}
			}
			session.removeAttribute("staff");
			List<Staff> allstaff = db.getAllStaff();
			session.setAttribute("staff", allstaff);
			
		}
		 
		
		
		}
		
		
		
		 
		 
		 else if(request.getParameter("numero")!=null) {
			 Date data = new Date();
			 SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			 String dataI = simple.format(data);
				
			    auto = db.getAutoNamedQuery();
				session.setAttribute("trovate", auto);
				noleggi = db.getNoleggioByNumero(Integer.parseInt(request.getParameter("numero")),dataI);
				session.setAttribute("noleggi", noleggi);
				clienti = db.getAllClienteAttivo();
				session.setAttribute("clienti", clienti);
				
		 }
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("nome")!=null) {
			Staff s = new Staff();
			s.setNome(request.getParameter("nome"));
			s.setCognome(request.getParameter("cognome"));
			s.setEmail(request.getParameter("email"));
			s.setPassword(request.getParameter("password"));
			db.addStaff(s);
			session = request.getSession();
			List<Staff> staff= db.getAllStaff();
			session.setAttribute("staff", staff);
		}
		
		if(request.getParameter("marca")!=null) {
			List<Categoria> lista = db.getCategoriaByName(request.getParameter("categoria"));
			Auto a = new Auto();
			
			
			    a.setMarca(request.getParameter("marca"));
				a.setModello(request.getParameter("modello"));
				a.setTarga(request.getParameter("targa"));
				a.setUrl(request.getParameter("url"));
			
				for (Categoria c : lista) {
				
				a.setCategoria(c);
				a.getCategoria().setId(c.getId());
				
			}
				
			db.addAuto(a);
			if(session.getAttribute("allauto")!=null) {
				session.removeAttribute("allauto");
			}
			if (session.getAttribute("manutenzione")!=null) {
				session.removeAttribute("manutenzione");
			}
			if (session.getAttribute("autodisponibili")!=null) {
				session.removeAttribute("autodisponibili");
			}
		}
		
		
		if(request.getParameter("visualizza")!=null) {
			Staff s = db.getStaffById(Integer.parseInt(request.getParameter("id")));
			s.setPassword(request.getParameter("password"));
			db.updateStaff(s);
			session = request.getSession();
			List<Staff> staff= db.getAllStaff();
			session.setAttribute("staff", staff);
		}
		
		if(request.getParameter("nomecategoria")!=null) {
			Categoria c = db.getCategoriaById(Integer.parseInt(request.getParameter("id")));
			c.setNome(request.getParameter("nomecategoria"));
			c.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
		    db.updateCategoria(c);
		    session = request.getSession();
		    List<Categoria> categorie = db.getAllCategoria();
			 session.setAttribute("allcategorie", categorie);
		}
		
		
		
		if(request.getParameter("aggiungicategoria")!=null) {
			Categoria c = new Categoria();
			c.setNome(request.getParameter("aggiungicategoria"));
			c.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
		    db.addCategoria(c);
		    session = request.getSession();
		    List<Categoria> categorie = db.getAllCategoria();
			 session.setAttribute("allcategorie", categorie);
		}
		
	    
		
		doGet(request, response);
	
	}
	
	
	
	
    
	private void updateCliente(Cliente cliente) {
		manager.getTransaction().begin(); 
		manager.merge(cliente);
		manager.getTransaction().commit(); 
		
	}
    
    
    
    
    
    
   
    
    

}
