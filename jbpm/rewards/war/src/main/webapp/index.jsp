<html>
<head>
<title>Rewards Basic example</title>
</head>
<body>
<p>Rewards Basic example</p>
<p><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></p>
<ul>
<li><a href="startProcess.jsp">Start Reward Process</a></li>
<li><a href="task?user=john&cmd=list">John's Task</a></li>
<li><a href="task?user=mary&cmd=list">Mary's Task</a></li>
</ul>
</body>
</html>