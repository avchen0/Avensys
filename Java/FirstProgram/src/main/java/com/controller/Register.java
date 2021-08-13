package com.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.Model;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String un = req.getParameter("un");
		String pwd= req.getParameter("pwd");
		String cpwd= req.getParameter("cpwd");
		
		if (pwd.equals(cpwd)) {
			Model m = new Model();
			m.setName(name);
			m.setEmail(email);
			m.setUn(un);
			m.setPwd(pwd);
			
			int rowsUpdated = m.register();
			if (rowsUpdated == 1) {
				String msg="Welcome, " + name + ". You have successfully registered under the username "
						+ un + "."; //mail body
				m.sendEmail(m.getEmail(), Credentials.email, Credentials.pwd, msg);
				resp.sendRedirect("/FirstProgram/successReg.html");
			}
			else if (rowsUpdated == -1){
				HttpSession session = req.getSession(true);
				session.setAttribute("email", m.getEmail());
				resp.sendRedirect("/FirstProgram/accExists.jsp");
			}
			else {
				resp.sendRedirect("/FirstProgram/didNotReg.html");
			}
		}
		else {
			resp.sendRedirect("/FirstProgram/wrongReg.html");
		}
		
		/*
		Random rand = new Random();
		int otp = rand.nextInt(900000) + 100000;
		
		 */
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
