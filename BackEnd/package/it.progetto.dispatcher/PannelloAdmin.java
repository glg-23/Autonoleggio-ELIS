package it.progetto.dispatcher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.progetto.entity.Admin;


/**
 * Servlet implementation class PannelloAdmin
 */
@WebServlet(name="admin" , urlPatterns = {"/admin"})
public class PannelloAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
     HttpSession session; 
     Admin admin;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PannelloAdmin() {
        super();
        admin = new Admin();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		admin = (Admin)session.getAttribute("loginAdmin");
		
		if(admin != null) {
		request.getServletContext().getRequestDispatcher("/jsp/header.jsp").include(request, response);
		request.getServletContext().getRequestDispatcher("/jsp/menuadmin.jsp").include(request, response);
		 
		if(request.getParameter("allauto")!=null) {
			
			if(request.getParameter("auto")!=null) {
				request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
			 if(request.getParameter("cancella")!=null) {
            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
            }
				request.getServletContext().getRequestDispatcher("/jsp/auto.jsp").include(request, response);
			}
			
			if(request.getParameter("aggiungi")!=null) {
				request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/aggiungiauto.jsp").include(request, response);
			}
            if(request.getParameter("disponibili")!=null) {
            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
            	 if(request.getParameter("cancella")!=null) {
 	            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
 	            }
            	request.getServletContext().getRequestDispatcher("/jsp/autodisponibili.jsp").include(request, response);
			}
            
            if(request.getParameter("manutenzione")!=null) {
            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
            	 if(request.getParameter("cancella")!=null) {
 	            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
 	            }
            	request.getServletContext().getRequestDispatcher("/jsp/automanutenzione.jsp").include(request, response);
			}
            
            if(request.getParameter("categoria")!=null) {
            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
            	if(request.getParameter("cancella")!=null) {
 	            	request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
 	            }
            	
            	request.getServletContext().getRequestDispatcher("/jsp/prezzi.jsp").include(request, response);
            	
            	
            }
           if(request.getParameter("modificacat")!=null) {
            		request.getServletContext().getRequestDispatcher("/jsp/modificaprezzi.jsp").include(request, response);
 	            }
           
           if(request.getParameter("addcategoria")!=null) {
       		request.getServletContext().getRequestDispatcher("/jsp/aggiungicategoria.jsp").include(request, response);
            }
			
			
			
		 } 
		 
		 else if (request.getParameter("noleggi")!=null) {
			 
			
			if (request.getParameter("attivi")!=null) {
			request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);
            
			if(request.getParameter("delete")!=null) {
            request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);	
			}
			
			request.getServletContext().getRequestDispatcher("/jsp/noleggiattivi.jsp").include(request, response);
			}
			
			if(request.getParameter("storico")!=null) {
				request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/tabellastorico.jsp").include(request, response);
			}
			 
		 }
		 
		
		 
		 else if (request.getParameter("clienti")!=null) {
			 if(request.getParameter("visualizza")!=null) {
					request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response); 
					 if( request.getParameter("cancellacliente")!=null) {
						 request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response); 
					 }
					
					request.getServletContext().getRequestDispatcher("/jsp/tabellaclienti.jsp").include(request, response);
					
					 }
					 
					 if(request.getParameter("approva")!=null) {
					request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);
					request.getServletContext().getRequestDispatcher("/jsp/approvaclienti.jsp").include(request, response);
					 }
					 
					 if(request.getParameter("approvatutti")!=null) {
							request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);
							request.getServletContext().getRequestDispatcher("/jsp/tabellaclienti.jsp").include(request, response);
							 }
			 
			  
		 }
		 
		
		 
		 
		 else if(request.getParameter("allstaff")!=null) {
			 
			 if(request.getParameter("visualizza")!=null) {
			request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response); 
			 if( request.getParameter("cancellastaff")!=null) {
				 request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response); 
			 }
			 
			request.getServletContext().getRequestDispatcher("/jsp/tabellastaff.jsp").include(request, response);
			
			
			
			 }
			 
			 if(request.getParameter("inserisci")!=null) {
			request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response);
			request.getServletContext().getRequestDispatcher("/jsp/inseriscistaff.jsp").include(request, response);
			 }
			 
			 if( request.getParameter("modifica")!=null) {
				 request.getServletContext().getRequestDispatcher("/jsp/modificastaff.jsp").include(request, response);
			 }
			
			 
			   	
			}
		 
		 
		 else if (request.getParameter("numero")!=null) {
			    request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/prenotazione.jsp").include(request, response); 
				 }
		
		 else {
		request.getServletContext().getRequestDispatcher("/jsp/homestaff.jsp").include(request, response);
		 }
		
		 
		request.getServletContext().getRequestDispatcher("/jsp/footer.jsp").include(request, response);
		
		
		}
		
		
		else {
			response.sendRedirect("index");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("nome")!=null || request.getParameter("marca")!=null || request.getParameter("password")!=null ) {
		request.getServletContext().getNamedDispatcher("controlleradmin").include(request, response); 	
		}
		
		doGet(request, response);
	}

}
