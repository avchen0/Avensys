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
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		int deposit = Integer.parseInt(req.getParameter("deposit"));
		Model m = new Model();
		HttpSession session = req.getSession(true);
		String email = (String) session.getAttribute("email");
		String pwd = (String) session.getAttribute("pwd");
		int balance = (int) session.getAttribute("balance");
		m.setEmail(email);
		m.setPwd(pwd);
		m.setBalance(balance);
		
		int rowsUpdated = m.modifyBalance(deposit, 1);
		if (rowsUpdated == 1) {
			session.setAttribute("balance", m.getBalance());
			String msg="You have successfully deposited $" + deposit +
					". Your updated account balance is $" + m.getBalance();
			m.sendEmail((String) session.getAttribute("email"), Credentials.email, Credentials.pwd, msg);
			
			session.setAttribute("msg", msg);
			resp.sendRedirect("/FirstProgram/balanceUpdated.jsp");
		}
		else {
			resp.sendRedirect("/FirstProgram/error.html");
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
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
