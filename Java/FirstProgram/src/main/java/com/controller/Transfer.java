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
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		String racno = req.getParameter("racno");
		int transfer = Integer.parseInt(req.getParameter("transfer"));
		
		Model m = new Model();
		HttpSession session = req.getSession(true);
		String email = (String) session.getAttribute("email");
		String pwd = (String) session.getAttribute("pwd");
		int balance = (int) session.getAttribute("balance");
		m.setEmail(email);
		m.setPwd(pwd);
		m.setBalance(balance);
		
		int rowsUpdated = m.transfer(transfer, racno);
		if (rowsUpdated == 2) {
			String msg="You have successfully transferred $" + transfer + " to account: " + racno + 
					". Your updated account balance is $" + m.getBalance();
			m.sendEmail((String) session.getAttribute("email"), Credentials.email, Credentials.pwd, msg);
			session.setAttribute("msg", msg);
			resp.sendRedirect("/FirstProgram/transferSuccess.jsp");
		}
		else if (rowsUpdated == -1){
			resp.sendRedirect("/FirstProgram/insufficientBalance.jsp");
		}
		else if (rowsUpdated == -2){
			resp.sendRedirect("/FirstProgram/invalidRecipient.jsp");
		}
		else {
			resp.sendRedirect("/FirstProgram/error.html");
		}
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
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
