<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.sample.ShoppingCart" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Result</title>
</head>
<body>
    <table>
		<tr><b>Here is your Order: </b></tr>
		<tr><td>Order Amount : </td><td><% out.println(((ShoppingCart)request.getSession().getAttribute("sc")).getAmount()); %></td></tr>
	    <tr><td>Discount:</td><td><% out.println(((ShoppingCart)request.getSession().getAttribute("sc")).getDiscount()); %></td></tr>
	    <tr><td>Total Invoice:</td><td><% out.println(((ShoppingCart)request.getSession().getAttribute("sc")).getAmount()-((ShoppingCart)request.getSession().getAttribute("sc")).getDiscount()); %></td></tr>
	
	  
      </table>
</body>
<jsp:include page="index.jsp"></jsp:include>
</html>


