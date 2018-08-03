<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Complete Task</title>
</head>
<body>
<form action="HumanTaskServlet" method="post" >
	<table>
		<!--   <tr>
			<b>Here is your Order: </b>
		</tr>
		<tr>
			<td>Order Amount :</td>
			<td>
				< %
					out.println(((ShoppingCart) request.getSession().getAttribute("sc"))
							.getAmount());
				%>
			</td>
		</tr>
		<tr>
			<td>Discount:</td>
			<td>
				< %
					out.println(((ShoppingCart) request.getSession().getAttribute("sc"))
							.getDiscount());
				%>
			</td>
		</tr>
		<tr>
			<td>Total Invoice:</td>
			<td>
				< %
					out.println(((ShoppingCart) request.getSession().getAttribute("sc"))
							.getAmount()
							- ((ShoppingCart) request.getSession().getAttribute("sc"))
									.getDiscount());
				%>
			</td>
		</tr>
		<tr>
			<td width="20%">Approval:</td>
			<td width="25%"><input type="radio" name="radios"
				value="true" checked>Yes</td>
			<td width="30%"><input type="radio" name="radios" value="false"
				>Rejected</td>
				
		</tr>  --> 
		</table>
</br>
<table>
		<tr></tr>
		<tr><td><input name="submit" type="submit" value="Complete" title="Complete"></td></tr>
	</table>
</form>
</body>
</html>