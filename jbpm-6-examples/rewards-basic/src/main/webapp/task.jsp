<%@ page import="org.kie.api.task.model.TaskSummary" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jBPM Rewards Demo</title>
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
			List<TaskSummary> tasklist = (List<TaskSummary>)request.getAttribute("taskList");
			String message = "no ticket";
			if(tasklist.size() == 1) {
				message = "1 ticket";
			} else if(tasklist.size() > 1){
				message = tasklist.size() + " tickets" ;
			}
			for (TaskSummary task : tasklist) { 
				
			%>
			<tr>
			<td><%= task.getName() %></td>
			<td><%= task.getId() %></td>
			<td><%= task.getProcessInstanceId() %></td>
			<td><a href="task?user=<%= user %>&taskId=<%= task.getId() %>&processId=<%= task.getProcessInstanceId() %>&cmd=approve">Approve</a></td>
			</tr>
			<% } %>
			</table>
			
			<p>
            	<label style="color: green;width: 100%;text-align: left;">
            		<%=message %>
            	</label> 
			</p>
			<br/>
			<%@ include file="menu.jsp"%>

        </div>
        
        <div id="footer">
            <%@ include file="foot.jsp"%>
        </div>
    </div>
</body>
</html>