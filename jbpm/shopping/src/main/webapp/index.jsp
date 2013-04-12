<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.sample.ShoppingCart" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu</title>
</head>
<body>
    <table>
	 
	  <td align="right">
	  	<form action="NavServlet" method="post">
  			<input align="right" name="submit" type="submit" value="Place an order" title="Place an order" />
  			<input align="right" name="submit" type="submit" value="Signal" title="Signal" />
  		</form>
  	  </td>
        
      <td align="right">
    	   <form action="HumanTaskServlet" method="post">
    			<input align="right" name="submit" type="submit" value="Retrieve" title="Retrive Tasks" /></tr>
   			</form>
   	  </td>
   
      
      </table>
</body>
</html>