<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="HumanTaskServlet" method="post" >

<table border="1">
<tr><h4>Group Tasks</h4></tr>
<tr>
	<td></td>
	<td>Task Id</td>
	<td>Task Name</td>
	<td>Process Id</td>
	<td>Task Status</td>
</tr>
<c:forEach items="${groupTasks}" var="task" >
<tr>
  <td><input name="taskId" type="checkbox" value="${task.id}" />
  <td><c:out value="${task.id}" /></td>
  <td><c:out value="${task.name}" /></td>
  <td><c:out value="${task.processId}" /></td>
  <td><c:out value="${task.status}" /></td>
  </tr>
</c:forEach>

</table>



</br>
<table border="1">
<tr><h4>Individual Tasks</h4></tr>
<tr>
	<td></td>
	<td>Task Id</td>
	<td>Task Name</td>
	<td>Process Id</td>
	<td>Task Status</td>
</tr>
<c:forEach items="${indTasks}" var="task" >

	<c:if test="${task.status eq 'Reserved'}"  >
		<tr>
  		<td><input name="taskId" type="checkbox" value="${task.id}" />
  		<td><c:out value="${task.id}" /></td>
  		<td><c:out value="${task.name}" /></td>
  		<td><c:out value="${task.processId}" /></td>
  		<td><c:out value="${task.status}" /></td>
  		</tr>
  	</c:if>
</c:forEach>

</table>
</br>
<table>
<tr>
	<td><input name="submit" type="submit" value="Claim" title="Claim a Task"></td>

	<td><input name="submit" type="submit" value="View" title="View a Task"></td>
</tr>
</table>
</form>
</body>
<jsp:include page="index.jsp"></jsp:include>
</html>