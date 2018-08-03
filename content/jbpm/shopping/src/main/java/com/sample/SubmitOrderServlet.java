package com.sample;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.task.query.TaskSummary;

public class SubmitOrderServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws IOException,ServletException{
	    this.doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		
		ProcessWorkflow pw = new ProcessWorkflow();
		pw.startWorkflow();
		System.out.println("ksessionId:"+pw.ksession.getId());
		System.out.println("ksessionId:"+pw.pi.getId());
		request.getSession().setAttribute("pid",pw.pi.getId());
		request.getSession().setAttribute("ksessionId", pw.ksession.getId());

		try {
			request.getRequestDispatcher("/taskList.jsp").forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
