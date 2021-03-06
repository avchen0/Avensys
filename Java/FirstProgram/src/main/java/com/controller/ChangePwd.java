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
 * Servlet implementation class ChangePwd
 */
@WebServlet("/ChangePwd")
public class ChangePwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		String pwd = req.getParameter("pwd");
		String newpwd = req.getParameter("newpwd");
		String cnewpwd = req.getParameter("cnewpwd");
		
		if (newpwd.equals(cnewpwd)) {
			Model m = new Model();
			HttpSession session = req.getSession(true);
			String email = (String) session.getAttribute("email");
			m.setEmail(email);
			m.setPwd(pwd);
			m.setNewpwd(newpwd);
			
			int rowsUpdated = m.changePwd();
			if (rowsUpdated == 1) {
				resp.sendRedirect("/FirstProgram/passwordChanged.jsp");
			}
			else {
				resp.sendRedirect("/FirstProgram/error.html");
			}
		}
		else {
			resp.sendRedirect("/FirstProgram/diffPasswords.html");
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePwd() {
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
