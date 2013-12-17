package org.jbpm.demo.approve.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.demo.approval.audit.AuditDAOFactory;
import org.jbpm.demo.approval.ejb.ProcessOperationException;
import org.jbpm.demo.approval.ejb.TaskExecuteServiceLocal;
import org.kie.api.task.model.TaskSummary;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private TaskExecuteServiceLocal taskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String cmd = req.getParameter("cmd");
        String user = req.getParameter("user");
        String processId = req.getParameter("processId");
        
        if (cmd.equals("list")) {
            List<TaskSummary> taskList;
            try {
                taskList = taskService.retrieveTaskList(user);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            req.setAttribute("taskList", taskList);
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/task.jsp");
            dispatcher.forward(req, res);
            return;
        } else if (cmd.equals("approve")) {
            String message = "";
            long taskId = Long.parseLong(req.getParameter("taskId"));
            try {
                taskService.approveTask(user, taskId);
                message = "Ticket (id = " + processId + ") has been approved by " + user;
            } catch (ProcessOperationException e) {
                // Recoverable exception
                message = "Ticket (id = " + processId + ") operation failed. Please retry : " + e.getMessage();
            } catch (Exception e) {
                // Unexpected exception
                throw new ServletException(e);
            }
            req.setAttribute("message", message);
            AuditDAOFactory.defaultDAO().addAudit(Long.parseLong(processId), message);
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, res);
            return;
        }

    }

}