<%@ page import="org.jbpm.demo.rewards.audit.*" %>
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
			
			<p style="FONT-SIZE: large;">Audit View</p>
			<table class="simpletablestyle">
			<thead>
				<tr>
					<th width="100">Ticket Id</th>
					<th>Audit</th>
				</tr>
			</thead>
			<% 
			String plusID = (String)request.getAttribute("plusid"); 
			Integer num = (Integer)request.getAttribute("pagenum"); 
			for (Audit audit : (List<Audit>)request.getAttribute("auditList")) { 
				 if(plusID.equals(audit.getId() + "")) { 
			%>
			<tr>
			<td><%= audit.getId() %></td>	
			<td><a href="audit?id=no"><%= audit.getAudit() %></a></td>
			</tr>
			<% for (Audit son : audit.getAudits()) {%>
			<tr>
			<td></td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<%= son.getAudit() %></td>	
			</tr>
			<%} %>
			<%} else {%>
			<tr>
			<td><%= audit.getId() %></td>	
			<td><a href="audit?id=<%= audit.getId() %>"><%= audit.getAudit() %></a></td>
			</tr>
			<% } %>
			<% } %>
			</table>
			
			
			<br/>
			
			<a href="audit?id=no&num=<%=num %>">Page <%=num %></a>&nbsp;&nbsp;&nbsp;<a href="audit?id=no&num=<%=num-1 %>">Previous</a>&nbsp;&nbsp;&nbsp;<a href="audit?id=no&num=<%=num+1 %>">Next</a>
			
			<br/>
			<%@ include file="menu.jsp"%>

        </div>
        
        <div id="footer">
            <%@ include file="foot.jsp"%>
        </div>
    </div>
</body>
</html>