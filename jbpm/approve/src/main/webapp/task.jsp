<%@ page import="org.jbpm.task.query.TaskSummary" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>Task management</title>
</head>
<body>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jBPM Approve Demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Here we include the css file  -->
<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
<head>

<body>
    <div id="container">
        
        <div id="content">
			
			<% String user = request.getParameter("user"); %>
			<p style="FONT-SIZE: large;"><%= user %>'s Task</p>
			<table class="simpletablestyle">
			<thead>
				<tr>
					<th>Task Name</th>
					<th>Task Id</th>
					<th>ProcessInstance Id</th>
					<th>Action</th>
				</tr>
			</thead>
			<% 
			
			List<Object> list = (List<Object>)request.getAttribute("taskList");
			for(Object obj : list) {
				org.jbpm.task.query.TaskSummary taskSummary = (org.jbpm.task.query.TaskSummary)obj;
				System.out.println(taskSummary);	
			}
			
			for (TaskSummary task : (List<TaskSummary>)request.getAttribute("taskList")) { 
			
			%>
			<tr>
			<td><%= task.getName() %></td>
			<td><%= task.getId() %></td>
			<td><%= task.getProcessInstanceId() %></td>
			<td><a href="task?user=<%= user %>&taskId=<%= task.getId() %>&cmd=approve">Approve</a></td>
			</tr>
			<% } %>
			</table>
			
			<%@ include file="menu.jsp"%>

        </div>
        
        <div id="footer">
            <%@ include file="foot.jsp"%>
        </div>
    </div>
</body>
</html>