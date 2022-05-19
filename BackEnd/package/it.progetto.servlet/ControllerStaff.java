package it.progetto.servlet;

import java.io.IOException;
import java.text.ParseException;
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
import it.progetto.entity.Notifiche;
import it.progetto.entity.Staff;
import it.progetto.persistence.Database;

/**
 * Servlet implementation class ControllerStaff
 */
@WebServlet(name = "controllerstaff" ,urlPatterns = {"/controllerstaff"})
public class ControllerStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	private EntityManagerFactory em;
	private EntityManager manager; 
	private List<Noleggia> noleggi;
	private List<Cliente> clienti;
	private List<Auto> auto;
	Auto a;
	private Database db;
	
    public ControllerStaff() {
        super();
        em=  Persistence.createEntityManagerFactory("autonoleggio");
		manager = em.createEntityManager();
		noleggi= new ArrayList<Noleggia>();
		clienti=new ArrayList<Cliente>();
		auto = new ArrayList<Auto>();
		a=  new Auto();
		db = Database.getIstance();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		
		if(request.getParameter("allauto")!=null) {
			auto= null;
				
				
				
				if(request.getParameter("auto")!=null) {
				if(session.getAttribute("allauto")==null) {
				auto = db.getAutoNamedQuery();
				session.setAttribute("allauto", auto);
				
				}
				else {
				auto = (List<Auto>)session.getAttribute("allauto");	
				}
				
				}
			
				
				if(request.getParameter("cancella")!=null) {
					auto = db.getAutoNamedQuery();
					for(Auto a : auto) {
						if(a.getId()==Integer.parseInt(request.getParameter("id"))) {
							db.removeAuto(a);
							
						}
					}
					if(session.getAttribute("allauto")!=null) {
						session.removeAttribute("allauto");
						
					}
					if (session.getAttribute("manutenzione")!=null) {
						session.removeAttribute("manutenzione");
					}
					if (session.getAttribute("autodisponibili")!=null) {
						session.removeAttribute("autodisponibili");
					}
					
					auto = db.getAutoNamedQuery();
					session.setAttribute("allauto", auto);
				}
				
				
				
				 if(request.getParameter("disponibili")!=null) {
					 if(session.getAttribute("autodisponibili")==null) {
					 auto = db.getAllAutoDisponibili();
					 session.setAttribute("autodisponibili", auto);
					 }
					 else {
				     auto = (List<Auto>)session.getAttribute("autodisponibili"); 
					 }
					 
					 if(request.getParameter("nondisponibile")!=null) {
						
						a = db.getAutoById(Integer.parseInt(request.getParameter("id")));
						a.setDisponibile(Integer.parseInt(request.getParameter("disponibile")));
						db.updateAuto(a);
						Calendar c = Calendar.getInstance();
						 SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
						 String dataI = simple.format(c.getTime());
						  
						 List<Notifiche> notific=null;
						 if(request.getParameter("disponibile").equals("1")) {
							noleggi = db.getAllNoleggi();
							for(Noleggia n : noleggi) {
								notific= db.getAllNotifiche();
								if(n.getIdAuto()==Integer.parseInt(request.getParameter("id"))){
									
									if(notific.isEmpty()) {
									Notifiche notifica = new Notifiche();
									notifica.setIdCliente(n.getIdCliente());
									notifica.setTesto("Ops! la tua auto non e' piu' disponibile <br> effettua un altro noleggio");
									db.addNotifica(notifica);		
									}
									else {
										for(Notifiche no : notific) {
											if(no.getIdCliente()==n.getIdCliente() && no.getTesto().equals("Ops! la tua auto non e' piu' disponibile <br> effettua un altro noleggio")) {
												
											}
											else {
												Notifiche notifica = new Notifiche();
												notifica.setIdCliente(n.getIdCliente());
												notifica.setTesto("Ops! la tua auto non e' piu' disponibile <br> effettua un altro noleggio");
												db.addNotifica(notifica);	
											}
										}
										
									}
									
									
									
								}
								
							}
							
							
							db.deleteNoleggioByAutoAndData(Integer.parseInt(request.getParameter("id")),dataI);
							session.removeAttribute("allauto");
							List<Auto> auto = db.getAutoNamedQuery();
							session.setAttribute("allauto", auto);
							session.removeAttribute("noleggi");
							session.removeAttribute("autodisponibili");
							List <Auto>automobili = db.getAllAutoDisponibili();
							session.setAttribute("autodisponibili", automobili);
							session.removeAttribute("manutenzione");
							
						}
						
						
						 
					 }
					 
					 
				 }
				 
				 
				 if(request.getParameter("manutenzione")!=null) {
					 
					  
					 if(session.getAttribute("manutenzione")==null) {
						 auto = db.getAllAutoManutenzione();
						 session.setAttribute("manutenzione", auto);
						 }
						 else {
						 auto = (List<Auto>)session.getAttribute("manutenzione"); 
						 }
					 
					 if(request.getParameter("nondisponibile")!=null) {
						   
						    
						 
						    a = db.getAutoById(Integer.parseInt(request.getParameter("id")));
						    a.setDisponibile(Integer.parseInt(request.getParameter("disponibile")));
						    db.updateAuto(a);
						    
						    Calendar c = Calendar.getInstance();
						    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
						    String dataI = simple.format(c.getTime());
						  
						 if (request.getParameter("disponibile").equals("0")) {
							session.removeAttribute("allauto");
							List<Auto> auto = db.getAutoNamedQuery();
							session.setAttribute("allauto", auto);
							session.removeAttribute("noleggi");
							session.removeAttribute("manutenzione");
							List <Auto>automobili = db.getAllAutoManutenzione();
							session.setAttribute("manutenzione", automobili);
							session.removeAttribute("autodisponibili");
						} 
						 
					 }
					
						
						
						
						
						
				 }
				
				 if(request.getParameter("categoria")!=null) {
					 List<Categoria> categorie = db.getAllCategoria();
					 session.setAttribute("allcategorie", categorie);
					 
					 if(request.getParameter("cancella")!=null) {
						 List<Categoria> categoria = db.getAllCategoria();
							for(Categoria c : categoria) {
								if(c.getId()==Integer.parseInt(request.getParameter("id"))) {
									db.removeCategoria(c);
									
								}
							}
				 }
					 
					 
				
				
			}
		}
		
		
		else if(request.getParameter("noleggi")!=null) {
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
			}else {
			    auto = (List<Auto>)session.getAttribute("allauto"); 
			}
			
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
		
		
		
		
		else if (request.getParameter("logoutstaff") != null) {
            session.removeAttribute("loginStaff");
            if(session.getAttribute("noleggi")!=null) {
                session.removeAttribute("noleggi");	
                }
            if(session.getAttribute("allauto")!=null) {
                session.removeAttribute("allauto");	
                }
            if(session.getAttribute("manutenzione")!=null) {
                session.removeAttribute("manutenzione");	
                }
            if(session.getAttribute("autodisponibili")!=null) {
                session.removeAttribute("autodisponibili");	
                }
            if(session.getAttribute("allcategorie")!=null) {
                session.removeAttribute("alcategorie");	
                }
			if(session.getAttribute("trovate")!=null) {
                session.removeAttribute("trovate");
                session.removeAttribute("clienti");
                }
			response.sendRedirect("index");
			
			
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
		
		if(request.getParameter("nomecategoria")!=null) {
			Categoria c = db.getCategoriaById(Integer.parseInt(request.getParameter("id")));
			c.setNome(request.getParameter("nomecategoria"));
			c.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
		    db.updateCategoria(c);
		    session = request.getSession();
		    List<Categoria> categorie = db.getAllCategoria();
			 session.setAttribute("allcategorie", categorie);
		}
		
		
	
		
	}
	
	
	
	
	

}
