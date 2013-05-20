package org.jbpm.demo.approve.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.demo.approve.audit.Audit;
import org.jbpm.demo.approve.audit.AuditDAOFactory;
import org.jbpm.demo.approve.test.AuditTest;

@WebServlet("/audit")
public class AuditServlet extends HttpServlet {

	private static final long serialVersionUID = 5091602640368671310L;
	
	private int pageNum = 1 , pageSize = 8;
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String plusid = req.getParameter("id");
		String numStr = req.getParameter("num");
		try {
			int num = Integer.parseInt(numStr);
			if(num <= 0) {
				pageNum = 1;
			} else {
				pageNum = num ;
			}
		} catch (NumberFormatException e) {
			pageNum = 1 ;
		}
		
		// This for test, will use DAO replace
		List<Audit>  audits = AuditDAOFactory.defaultDAO().getAudits(pageNum, pageSize);
		
		req.setAttribute("auditList", audits);
		req.setAttribute("pagenum", pageNum);
		req.setAttribute("plusid", plusid);
        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/audit.jsp");
        dispatcher.forward(req, resp);
        return;		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
