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
 * Servlet implementation class ResetPwd
 */
@WebServlet("/ResetPwd")
public class ResetPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		String userOtp = req.getParameter("otp");
		String newpwd = req.getParameter("newpwd");
		String cnewpwd = req.getParameter("cnewpwd");
		
		HttpSession sess = req.getSession(); // don't need true because it was already created
		String email = (String) sess.getAttribute("toEmail");
		int otp = (int) sess.getAttribute("otp");
		
		if (userOtp.equals(String.valueOf(otp))) {
			if (newpwd.equals(cnewpwd)) {
				Model m = new Model();
				m.setEmail(email);
				m.setNewpwd(cnewpwd);
				
				int rowsUpdated = m.resetPwd();
				
				if (rowsUpdated == 1) {
					resp.sendRedirect("/FirstProgram/resetSuccess.html");
				}
				else {
					resp.sendRedirect("/FirstProgram/resetError.html");
				}
			}
			else {
				sess.setAttribute("errorMessage", "Your password and Confirm New Password fields do not match.");
				resp.sendRedirect("/FirstProgram/diffInput.jsp");
			}
		}
		else {
			sess.setAttribute("errorMessage", "You have entered a different OTP.");
			resp.sendRedirect("/FirstProgram/diffInput.jsp");
		}
		
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPwd() {
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
