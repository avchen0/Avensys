<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Different Input Detected</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<%out.println(session.getAttribute("errorMessage"));%>
Click <a href="resetPwd.html">here</a> to try again.
</body>
</html>