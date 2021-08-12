package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.Model;

/**
 * Servlet implementation class FetchDataMVCServlet
 */
@WebServlet("/FetchDataMVCServlet")
public class FetchDataMVCServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = req.getParameter("name");
		
		Model m = new Model();
		
		m.setName(name);
		m.fetch();
		
		String email = m.getEmail();
		String role = m.getRole();
		
		// data from servlet to jsp
		// memory space : session
		
		// create a session
		HttpSession session = req.getSession(true); // true since session is created for the 1st time
		session.setAttribute("name", name);
		session.setAttribute("email", email);
		session.setAttribute("role", role);
		
		resp.sendRedirect("/FirstProgram/dataDisp.jsp");
	}
}
