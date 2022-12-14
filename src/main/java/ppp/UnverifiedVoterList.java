package ppp;

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UnverifiedVoterList
 */
@WebServlet("/UnverifiedVoterList")
public class UnverifiedVoterList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnverifiedVoterList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); 
		String cssTag="<link rel='stylesheet' type='text/css' href='table.css'>";

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from voters where Verified IS NULL");
			
			out.println("<html>");
			out.println("<head><title>UnVerified Voter List</title>"+cssTag+"</head>");
			out.println("<body>");
			out.print("<img  src=\"logo.png\" class=\"logo\"></img>");
			out.print("<table width=50% border=1 align=center>");  
			out.print("<h1 align='center'>UnVerified Voter List</h1>"); 
			
			
			ResultSetMetaData rsmd=rs.getMetaData();  
			//int total=rsmd.getColumnCount();  
			out.print("<tr>");  
			for(int i=1;i<=7;i++)  
			{  
			out.print("<th>"+rsmd.getColumnName(i)+"</th>");  
			}  
			out.print("<th>"+rsmd.getColumnName(10)+"</th>"); 
			out.print("<th>"+rsmd.getColumnName(12)+"</th>");
			out.print("<th> Verify   </th>");
			
			out.print("</tr>");  
			while(rs.next())  
			{  
				
			out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td><td><a href=./ViewPhoto?emailid="+rs.getString(7)+">View </a></td><td><a href=./ViewAadharcard?emailid="+rs.getString(7)+">View </a></td><td><a href=./VerifyVoterInfo?emailid="+rs.getString(7)+">Verify </a></td></tr>");  
			                  
			}  
			out.print("</table><br><br>"); 
			
			out.print("<button class=\"backbutton\" onclick=\"location.href='./VerifyVoter'\">Back</button>");
            out.println("</body></html>");
            response.setHeader("Cache-Control", "private, no-store, no-cache,must-revalidate");
			 response.setHeader("Pragma", "no-cache");
		}
		catch(Exception e2) 
		{
			e2.printStackTrace();
		}  
		          
		finally
		{
			out.close();
			
		}  

	}

	

}
