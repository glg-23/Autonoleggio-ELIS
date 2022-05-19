package it.progetto.dispatcher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.progetto.entity.Staff;

/**
 * Servlet implementation class PannelloStaff
 */
@WebServlet(name="staff", urlPatterns = {"/staff"})
public class PannelloStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	Staff staff;
    
    public PannelloStaff() {
        super();
        staff = new Staff();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		staff = (Staff)session.getAttribute("loginStaff");
		
		if(staff != null) {
		request.getServletContext().getRequestDispatcher("/jsp/header.jsp").include(request, response);
		request.getServletContext().getRequestDispatcher("/jsp/menustaff.jsp").include(request, response);
		
		
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
	            
	            
			
			
		 } 
		 
		 else if (request.getParameter("noleggi")!=null) {
			request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
			if(request.getParameter("delete")!=null) {
	            request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);	
				}
			request.getServletContext().getRequestDispatcher("/jsp/tabellanoleggi.jsp").include(request, response); 
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
		request.getServletContext().getNamedDispatcher("controllerstaff").include(request, response);
		doGet(request, response);
	}

}
