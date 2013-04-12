<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Entry</title>
</head>
<body>
  <form action="SubmitOrderServlet" method="post">
  	<table border="0">
  	    <!--  tr align="center"> <h1>Enter your order</h1></tr>
  		<tr>
  			
  		   <td >
  		   Item Name
  		   </td>
  		   <td>
  		   Amount
  		   </td>
  		</tr>
  		<tr>
  			
  		   <td>
  		   		<input type="text" name="itemName1" style=" width : 235px;" value="Item 1">
  		   </td>

  		   <td>
  		   		<input type="text" name="itemAmt1" style=" width : 235px;" value="100">
  		   </td>
  		</tr>
  		<tr>
  		    <td>
  		   		<input type="text" name="itemName2" style=" width : 235px;" value="Item 2">
  		   </td>
  		   <td>
  		   		<input type="text" name="itemAmt2" style=" width : 235px;" value="100">
  		   </td>
  		</tr>
  		<tr>   
  		    <td>
  		   		<input type="text" name="itemName3" style=" width : 235px;" value="Item 3">
  		   </td>
  		   <td>
  		   		<input type="text" name="itemAmt3" style=" width : 235px;" value="100">
  		   </td>
  		</tr>
  	    </br -->
    </table>
    <table>
    	<tr align="center"><input align="right" name="submit" type="submit" value="submit" title="Start Process" /></tr>
    </table>
  </form>
</body>
<jsp:include page="index.jsp"></jsp:include>
</html>