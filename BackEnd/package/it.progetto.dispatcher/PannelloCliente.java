package it.progetto.dispatcher;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import it.progetto.entity.Cliente;
import it.progetto.entity.Notifiche;
import it.progetto.persistence.Database;

/**
 * Servlet implementation class PannelloCliente
 */
@WebServlet(name = "index", urlPatterns = {"/index"})
public class PannelloCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	Cliente cliente;
	Database db;
    public PannelloCliente() {
        super();
        cliente = new Cliente();
        db =Database.getIstance();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		cliente = (Cliente) session.getAttribute("loginCliente");
		List<Notifiche> notifica = db.getAllNotifiche();
		for(Notifiche n : notifica) {
			if(cliente!=null) {
			if(n.getIdCliente()==cliente.getId()) {
				session.setAttribute("notifica", notifica);
			}
			}
		}
		
		if(cliente != null ) {
			request.getServletContext().getRequestDispatcher("/jsp/header2.jsp").include(request, response);
			request.getServletContext().getRequestDispatcher("/jsp/menu.jsp").include(request, response);
			
			
			if(request.getParameter("allauto")!=null) {
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				
				request.getServletContext().getRequestDispatcher("/jsp/tabellaauto.jsp").include(request, response);
				}
			
			
			else if(request.getParameter("preventivo")!=null) {
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				if(request.getAttribute("disponibili")!=null ){
					request.getServletContext().getRequestDispatcher("/jsp/find.jsp").include(request, response);
				}else {
					response.sendRedirect("index");
				}
			}
			
			
			
			else if (request.getParameter("prenota")!=null){
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/dettaglioCarrello.jsp").include(request, response);
			}
			
				
			
				else if(request.getParameter("noleggio")!=null) {
					request.getServletContext().getNamedDispatcher("auto").include(request, response);
					request.getServletContext().getRequestDispatcher("/jsp/conferma.jsp").include(request, response);
				}
			
				else if(request.getParameter("chisiamo")!=null) {
					request.getServletContext().getRequestDispatcher("/jsp/chisiamo.jsp").include(request, response);
				}
			
				else if(request.getParameter("contatti")!=null) {
					request.getServletContext().getRequestDispatcher("/jsp/contatti.jsp").include(request, response);
				}
			
				
			
			else {
			request.getServletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
			}
			
			
			request.getServletContext().getRequestDispatcher("/jsp/footer2.jsp").include(request, response);
		 }
		
		
		
		
		else if (cliente == null){
			request.getServletContext().getRequestDispatcher("/jsp/header2.jsp").include(request, response);
			request.getServletContext().getRequestDispatcher("/jsp/menu.jsp").include(request, response);
			
			if(request.getParameter("registrati")!=null) {
				request.getServletContext().getRequestDispatcher("/jsp/register.jsp").include(request, response);
			}
			
			else if(request.getParameter("login")!=null) {
				request.getServletContext().getRequestDispatcher("/jsp/login.jsp").include(request, response);
			}
			
			
			else if (request.getParameter("loginstaff")!=null) {
			request.getServletContext().getRequestDispatcher("/jsp/loginstaff.jsp").include(request, response);
			}
			
			else if(request.getParameter("allauto")!=null) {
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/tabellaauto.jsp").include(request, response);
				}
			
			else if(request.getParameter("preventivo")!=null) {
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				if(request.getAttribute("disponibili")!=null){
					request.getServletContext().getRequestDispatcher("/jsp/find.jsp").include(request, response);
				}else {
					response.sendRedirect("index");
				}
				
			}
			
			else if (request.getParameter("prenota")!=null){
				request.getServletContext().getNamedDispatcher("auto").include(request, response);
				request.getServletContext().getRequestDispatcher("/jsp/dettaglioCarrello.jsp").include(request, response);
			}
			
			
			
			else if(request.getParameter("chisiamo")!=null) {
				request.getServletContext().getRequestDispatcher("/jsp/chisiamo.jsp").include(request, response);
			}
		
			else if(request.getParameter("contatti")!=null) {
				request.getServletContext().getRequestDispatcher("/jsp/contatti.jsp").include(request, response);
			}
			
			
			else {
				request.getServletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
				
			}
			
				
		request.getServletContext().getRequestDispatcher("/jsp/footer2.jsp").include(request, response);
		
		}
			
			
		
		}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getServletContext().getNamedDispatcher("controller").include(request, response);	
		request.getServletContext().getNamedDispatcher("auto").include(request, response);	
		if(request.getParameter("modifica")!=null) {
			request.getServletContext().getNamedDispatcher("controller").include(request, response);		
		}
		if(request.getParameter("mail")!=null) {
			request.getServletContext().getNamedDispatcher("mail").include(request, response);		
		}
		doGet(request, response);
	}

}
