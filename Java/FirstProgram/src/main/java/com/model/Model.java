package com.model;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.controller.Credentials;

public class Model {
	
	Connection con;
	PrintWriter pw;
	PreparedStatement pstmt;
	ResultSet res;
	// instance variables correspond to the columns in the employee table
	private String name;
	private String email;
	private String role;
	
	private String un;
	private String pwd;
	private String newpwd;
	private String acno;
	private int balance;
	
	public Model() {
		connect();
	}

	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public String getUn() {
		return un;
	}
	public String getPwd() {
		return pwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public String getAcno() {
		return acno;
	}
	public int getBalance() {
		return balance;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public void setAcno(String acno) {
		this.acno = acno;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}	
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dxc", "root", "root");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fetch() {
		try {
			String sql = "SELECT * FROM users WHERE name =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			res = pstmt.executeQuery();
			// https://stackoverflow.com/a/28165814
			ResultSetMetaData rsmd = res.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			if (res.next()) {
				name = res.getString("name");
				email = res.getString("email");
				role = res.getString("role");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int register() {
		try {
			String sql = "Select * from users where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				setEmail(res.getString("email"));
				return -1;
			}
			else {
				sql = "Insert into users values (?,?,?,?,?,?)";
				Date date = new Date();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, un);
				pstmt.setString(4, pwd);
				pstmt.setLong(5, date.getTime());
				pstmt.setInt(6, 0);
				return pstmt.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int login() {
		try {
			String sql = "SELECT * from users where email=? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			
			res = pstmt.executeQuery();
			if (res.next()) {
				name = res.getString("name");
				un = res.getString("username");
				acno = res.getString("acno");
				balance = res.getInt("balance");
				return 1;
			}
			else {
				return 0;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int changePwd() {
		try {
			String sql1 = "Select * from users where email=? AND password=?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			res = pstmt.executeQuery();
			if (res.next()) {
				String sql2 = "UPDATE USERS SET PASSWORD=? WHERE EMAIL=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, newpwd);
				pstmt.setString(2, email);
				return pstmt.executeUpdate();
			}
			else {
				return 0;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int modifyBalance(int amount, int plusMinus) {
		try {
			String sql1 = "Select * from users where email=? AND password=?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			res = pstmt.executeQuery();
			if (res.next()) {
				if (plusMinus == -1) {
					if (res.getInt("balance") < amount) {
						return -1;
					}
				}
				String sql2 = "UPDATE users SET balance=? WHERE email=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, balance + amount * plusMinus);
				pstmt.setString(2, email);
				int x = pstmt.executeUpdate();
				setBalance(balance + amount * plusMinus);
				return x;
			}
			else {
				return 0;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int transfer(int transfer, String racno) {
		try {
			String sql1 = "Select * from users where email=? AND password=?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			res = pstmt.executeQuery();
			if (res.next()) {
				if (res.getInt("balance") < transfer) {
					return -1;
				}
				else {
					String sendSql = "UPDATE users SET balance=? WHERE email=? AND password=?";
					pstmt = con.prepareStatement(sendSql);
					System.out.printf("%d, %d\n", balance, transfer);
					pstmt.setInt(1, balance - transfer);
					pstmt.setString(2, email);
					pstmt.setString(3, pwd);
					int x = pstmt.executeUpdate();
					setBalance(balance - transfer);
					String receiveBalanceSql = "Select * from users where acno=?";
					pstmt = con.prepareStatement(receiveBalanceSql);
					pstmt.setString(1, racno);
					res = pstmt.executeQuery();
					if (res.next() == true) {
						String receiveSql = "UPDATE users SET balance=? WHERE acno=?";
						pstmt = con.prepareStatement(receiveSql);
						pstmt.setInt(1, res.getInt("balance") + transfer);
						pstmt.setString(2, racno);
						int y = pstmt.executeUpdate();
						return x + y;
					}
					else {
						return -2;
					}
					
				}
			}
			else {
				return 0;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int resetPwd() {
		String sql = "Update users set password=? where email=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpwd);
			pstmt.setString(2, email);

			int rowsUpdated = pstmt.executeUpdate();
			return rowsUpdated;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public void sendEmail(String toEmail, String fromEmail, String pwd, String msg) {
		String subject="DO NOT REPLY: Mail from Java Program"; // mail subject line
		
		//Creating Session Object
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				//sender's mail id and pwd is encapsulated inside "SendersCredentials.java"
				return new PasswordAuthentication(fromEmail, pwd);
			}
		});

		
		try {
			//Composing the Mail
			MimeMessage mesg = new MimeMessage(session);
			mesg.setFrom(new InternetAddress(fromEmail));
			mesg.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
			mesg.setSubject(subject);  
			mesg.setText(msg);  
			
			//Sending the Mail
			Transport.send(mesg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
