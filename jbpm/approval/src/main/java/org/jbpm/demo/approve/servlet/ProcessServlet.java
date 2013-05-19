package org.jbpm.demo.approve.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.demo.approve.audit.AuditDAOFactory;
import org.jbpm.demo.approve.ejb.ProcessService;

@WebServlet("/process")
public class ProcessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProcessService processService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            	
        String recipient = req.getParameter("recipient");
        String ticketname =req.getParameter("ticketname");

        long processInstanceId = -1;
        try {
            processInstanceId = processService.startProcess(recipient, ticketname);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        String message =  "Ticket (id = " + processInstanceId + ") has been started by " + recipient ;
        req.setAttribute("message",  message);
        AuditDAOFactory.defaultDAO().addAudit(processInstanceId , message);
        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, res);
    }
}