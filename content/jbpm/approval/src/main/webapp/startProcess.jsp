<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jBPM Approval Demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>

<body>
	
	<script language="Javascript">
	function validateForm()
	{
	var x=document.forms["applyForm"]["recipient"].value;
	var y=document.forms["applyForm"]["ticketname"].value;
	if (x==null || x=="")
	  {
	  alert("Applicant name must be filled out");
	  return false;
	  }
	if (y==null || y=="")
	  {
	  alert("Ticket Content can not be null");
	  return false;
	  } 
	}
	</script>
    
	<form name="applyForm" method="post" action="process" onsubmit="return validateForm();">
		Ticket &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="ticketname" size="20"><br>
		Applicant <input type="text" name="recipient" size="20"><br>
		<input type="submit" value="Apply">
	</form>
    
</body>
</html>