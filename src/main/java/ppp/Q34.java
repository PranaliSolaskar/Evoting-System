package ppp;
import java.io.PrintWriter; 

import java.sql.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Q34
 */
@WebServlet("/Q34")
public class Q34 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Q34() {
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
		PrintWriter pw; 
		response.setContentType("text/html"); pw=response.getWriter(); 
		String name=request.getParameter("name");
		String bname=request.getParameter("bname");
		String bpy=request.getParameter("bpy");
		String email=request.getParameter("email");

		try

		{

		Class.forName("com.mysql.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root",""); 

		PreparedStatement pstmt=con.prepareStatement("insert into bp values (?,?,?,?)"); 
		pstmt.setString(1, name);
		pstmt.setString(2, bname); 
		pstmt.setString(3,bpy); 
		pstmt.setString(4, email);
		int x=pstmt.executeUpdate();

		if(x==1)

		{

		pw.println("Values Inserted Successfully");

		}

		}

		catch(Exception e)

		{

		e.printStackTrace();

		} 

	}

}
