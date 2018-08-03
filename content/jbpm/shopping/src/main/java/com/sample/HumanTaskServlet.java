package com.sample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.instance.impl.demo.SystemOutWorkItemHandler;
import org.jbpm.process.workitem.wsht.AsyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.jms.JMSTaskClientConnector;
import org.jbpm.task.service.jms.JMSTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;

@SuppressWarnings("serial")
public class HumanTaskServlet extends HttpServlet {	

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		System.out.println("hello");
		System.out.println(request.getParameter("submit"));
		
		if (request.getParameter("submit").equals("Retrieve")){
			ProcessWorkflow pw = new ProcessWorkflow();
			try {
				List<TaskSummary> tsGroup = pw.retrieveGroupTaskList("HR");
				request.getSession().setAttribute("groupTasks", tsGroup);
			
				List<TaskSummary> tsInd = pw.retrieveIndTaskList("mary","HR");
				request.getSession().setAttribute("indTasks", tsInd);
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
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
		if (request.getParameter("submit").equals("Claim")){
			ProcessWorkflow pw = new ProcessWorkflow();
			try {
				String [] strArr = null;
				strArr = request.getParameterValues("taskId");
				for (int i = 0; i < strArr.length; i++) {
					pw.claimTask("mary", strArr[i], "HR");
				}
				
				System.out.println("Claimed");
				List<TaskSummary> tsGroup = pw.retrieveGroupTaskList("HR");
				request.getSession().setAttribute("groupTasks", tsGroup);
			
				List<TaskSummary> tsInd = pw.retrieveIndTaskList("mary","HR");
				request.getSession().setAttribute("indTasks", tsInd);
				//System.out.println(request.getParameter("taskId").length());
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
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
		if (request.getParameter("submit").equals("View")){
			ProcessWorkflow pw = new ProcessWorkflow();
			try {
				String [] strArr = null;
				strArr = request.getParameterValues("taskId");
				TaskSummary task = null;
				List<TaskSummary> list = (List<TaskSummary>)request.getSession().getAttribute("indTasks");
				if(strArr != null && list != null && list.size()>0 && strArr.length > 0){
					String taskId = strArr[0];
					System.out.println("array 0: " + Long.parseLong(taskId));
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						TaskSummary taskSummary = (TaskSummary) iterator.next();
					System.out.println("task id : " + taskSummary.getId() );
					if(Long.parseLong(taskId) == taskSummary.getId()){
						System.out.println("In if statement");
						task = taskSummary;
						request.getSession().setAttribute("task", task);
						pw.startTask( "mary", task.getId());
						}
				}
				}
				
				
				System.out.println("Viewing");
				//System.out.println(request.getParameter("taskId").length());
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
			try {
				request.getRequestDispatcher("/completeTask.jsp").forward(request,response);
			} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (request.getParameter("submit").equals("Complete")){
			ProcessWorkflow pw = new ProcessWorkflow();
			System.out.println("creating processworkflow item");
			Map<String, Object> taskParams = new HashMap<String, Object>();
			try {
				System.out.println("completing task");
				TaskSummary task = (TaskSummary) request.getSession()
						.getAttribute("task");
				System.out.println("task......."+task.getId());
				if (request.getParameter("radios") != null) {
					System.out.println("Checking Radio Buttons");
					System.out.println(request.getParameter("radios"));
					if (request.getParameter("radios").equals("true")) {
						System.out.println("Radio Button: true");
						taskParams.put("ApproveOut", true);
					} else {
						System.out.println("Radio Button: false");
						taskParams.put("ApproveOut", false);
					}
				}
				pw.completeTask("mary", task, taskParams);
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
			try {
				request.getRequestDispatcher("/taskConfirmation.jsp").forward(request,response);
			} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}