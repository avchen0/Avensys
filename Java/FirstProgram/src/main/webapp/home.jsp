<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<%out.println("Welcome " + session.getAttribute("name"));%>
<br><br>Click <a href="changePwd.html">here</a> to change your password.
<br><br>Click <a href="checkBalance.jsp">here</a> to check your balance.
<br><br>Click <a href="LogOut">here</a> to log out.
<br><br>Click <a href="deposit.html">here</a> to deposit money.
<br><br>Click <a href="transfer.html">here</a> to transfer money to another account.
<br><br>Click <a href="withdraw.html">here</a> to withdraw money.
</body>
</html>