

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Intro
 */
@WebServlet("/Intro")
public class Intro extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		PrintWriter pw = resp.getWriter();
		pw.println("Printing from Servlet file instead of html file" + System.getProperty("line.separator"));
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dxc", "root", "root");
			String name = req.getParameter("name");
			String sql = "SELECT * FROM employee WHERE name =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet res = pstmt.executeQuery();
			// https://stackoverflow.com/a/28165814
			ResultSetMetaData rsmd = res.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (res.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
		           if (i > 1) {
		        	   pw.print(",  ");
		           }
		           String columnValue = res.getString(i);
		           pw.print(columnValue);
				}
				pw.println(System.getProperty("line.separator"));
			}
		} catch (Exception e) { // e is either ClassNotFoundException or SQLException
			e.printStackTrace();
		}
		
		//resp.sendRedirect("FirstProgram/src/webapp/hello.html");
	}
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dxc", "root", "root");
		} catch (Exception e) { // e is either ClassNotFoundException or SQLException
			e.printStackTrace();
		}
	}	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Intro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()).append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
