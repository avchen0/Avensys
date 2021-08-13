<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Exists</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<%out.println("An account was previously registered under " + session.getAttribute("email") + ".\n");%>
<br><br>
Click <a href="login.html">here</a> to log in.<br><br>
Click <a href="register.html">here</a> to register under a different email.<br><br>
Click <a href="forgotPwd.html">here</a> to reset your password.<br>
</body>
</html>