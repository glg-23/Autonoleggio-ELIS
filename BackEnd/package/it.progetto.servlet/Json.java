package it.progetto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.google.gson.Gson;

import it.progetto.entity.Cliente;

/**
 * Servlet implementation class Json
 */
@WebServlet("/Json")
public class Json extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory em;
	private EntityManager manager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Json() {
        super();
        em=  Persistence.createEntityManagerFactory("autonoleggio");
		manager = em.createEntityManager();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Cliente> client = getAllClienteAttivo();
		
		Gson gson = new Gson();
		String cliente = gson.toJson(client);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(cliente);
		writer.flush();
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<Cliente> getAllClienteAttivo(){
		Query sql = manager.createQuery("SELECT c FROM Cliente c WHERE c.stato = 1 ");
		return sql.getResultList();
	}

}
