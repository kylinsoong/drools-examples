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
        <div align="right" class="dualbrand">
            <img src="resources/gfx/bpmn2_process.png" />
        </div>
        <div id="content">

            <p style="FONT-SIZE: large;">jBPM 6 Rewards Demo</p>

            <%@ include file="startProcess.jsp"%>
            
            <p>
            	<label style="color: green;width: 100%;text-align: left;">
            		<%= request.getAttribute("message") == null ? "no action" : request.getAttribute("message") %>
            	</label> 
			</p>
			<br/>
			
			<%@ include file="menu.jsp"%>
			

        </div>
        <div id="aside">
            <p>Learn more about jBPM</p>
            <ul>
               <li><a href="http://www.jboss.org/jbpm">jBPM Community</a></li>
               <li><a href="https://github.com/kylinsoong/jBPM-Drools-Example">QuickStart Demo</a></li>
            </ul>
            <p>Learn more about Infinispan</p>
            <ul>
                <li><a href="http://www.jboss.org/infinispan">Infinispan Community</a></li>
                <li><a href="https://github.com/kylinsoong/cluster">QuickStart Demo</a></li>
            </ul>
        </div>
        <div id="footer">
            <%@ include file="foot.jsp"%>
        </div>
    </div>
</body>
</html>
