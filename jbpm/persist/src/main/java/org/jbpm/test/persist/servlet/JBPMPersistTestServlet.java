package org.jbpm.test.persist.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test.do")
public class JBPMPersistTestServlet extends HttpServlet {

	private static final long serialVersionUID = 5386275090654917513L;
	
	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
    private EntityManagerFactory emf;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		System.out.println("-------------- jBPM Persistence Test Start -------------");
		System.out.println(emf);
	}
	
	protected void namedQueryTest() {
		
		System.out.println("Output NamedQueryTest Result:");
		
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("namedQueryTest");
		for (Object obj : query.getResultList()) {
			System.out.println("  " + obj);
		}
		em.close();
	}


	
}
