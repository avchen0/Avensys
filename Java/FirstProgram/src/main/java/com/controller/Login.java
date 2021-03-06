package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.Model;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");
		
		Model m = new Model();
		m.setEmail(email);
		m.setPwd(pwd);
		
		int rowsUpdated = m.login();
		if (rowsUpdated == 1) {
			String name = m.getName();
			String un = m.getUn();
			String acno = m.getAcno();
			int balance = m.getBalance();
			HttpSession session = req.getSession(true);
			session.setAttribute("name", name);
			session.setAttribute("un", un);
			session.setAttribute("pwd", pwd);
			session.setAttribute("acno", acno);
			session.setAttribute("balance", balance);
			session.setAttribute("email", email);
			resp.sendRedirect("/FirstProgram/home.jsp");
		}
		else {
			resp.sendRedirect("/FirstProgram/loginFailed.html");
		}
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
