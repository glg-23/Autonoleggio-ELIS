package it.progetto.servlet;

import java.io.IOException;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mail
 */
@WebServlet( name = "mail", urlPatterns = {"/mail"})
public class Mail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String username = "noleggiova@gmail.com";
    final String password = "noleggiova_1";
    
    public Mail() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	
	protected void processRequest(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException, IOException {
		      try {
		        Properties props = new Properties();
		        props.put("mail.smtp.host", "smtp.gmail.com");
		        props.put("mail.smtp.port", "587");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.starttls.enable", "true");
		        
		        Session session = Session.getInstance(props,
		                new javax.mail.Authenticator() {
		                    protected PasswordAuthentication getPasswordAuthentication() {
		                        return new PasswordAuthentication(username, password);
		                    }
		                });
		        
		        
		        Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("" + "<" +request.getParameter("email")+  ">"));
	            
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("noleggiova@gmail.com")
	            );
	            
	            
	            message.setSubject("richiesta autonoleggio");
	            message.setText("Inviato da: "+ request.getParameter("email") + " \n "+request.getParameter("message"));

	            Transport.send(message);

	            System.out.println("Inviata");
	            
		       
		        response.sendRedirect("index?contatti");
		      } catch (MessagingException ex) {
		        ex.printStackTrace();
		      }
		   }

		   @Override
		   protected void doGet(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException {
		       processRequest(request, response);
		   }

		   @Override
		   protected void doPost(HttpServletRequest request, HttpServletResponse response)
		     throws ServletException, IOException {
		       processRequest(request, response);
		   }

		   
		

}
