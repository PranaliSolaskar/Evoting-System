package ppp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=null;
		String city=null;
		String constitution=null;
		String age=null;
		String mobileno=null;
		String emailid=null;
		String username=null;
		String password=null;
		InputStream photo=null;
		InputStream aadharcard=null;
		boolean isMultiPartData=ServletFileUpload.isMultipartContent(request);
		
		if(isMultiPartData)
		{
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload fileupload=new ServletFileUpload(factory);
			File tempfile=new File("E:\\\\Photo");
			factory.setRepository(tempfile);
			
			try {
					List<FileItem> items=fileupload.parseRequest(request);
					for(FileItem item: items)
					{
						if(item.isFormField())
						{
							String fieldname=item.getFieldName();
							if(fieldname.equals("name"))
							{
								name=item.getString();
								
							}
							else if(fieldname.equals("city"))
							{
								city=item.getString();
							}
							else if(fieldname.equals("constitution"))
							{
								constitution=item.getString();
							}
							else if(fieldname.equals("age"))
							{
								age=item.getString();
							}
							else if(fieldname.equals("mobileno"))
							{
								mobileno=item.getString();
							}
							else if(fieldname.equals("emailid"))
							{
								emailid=item.getString();
								username=emailid;
							}

							else if(fieldname.equals("password"))
							{
								password=item.getString();
							}
						}
						else
						{
							if(item.getFieldName().equals("photo"))
							{
								photo= item.getInputStream();
							}
							if(item.getFieldName().equals("aadharcard"))
							{
								aadharcard=item.getInputStream();
							}
							
						}
					}
			} 
			catch (FileUploadException e) {
			
				e.printStackTrace();
			}	
		}
		else
		{
			System.out.println("its not multipart data");
		}
		
	    PrintWriter out=response.getWriter();
		
		int otp=new Random().nextInt(900000)+100000;
		String votingno="UVP"+otp;
		
		
		
		
		   try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
				
				PreparedStatement ps=con.prepareStatement("insert into voters values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, votingno);
				ps.setString(2, name);
				ps.setString(3, city);
				ps.setString(4, constitution);
				ps.setString(5, age);
				ps.setString(6, mobileno);
				ps.setString(7, emailid);
				ps.setString(8, username);
				ps.setString(9, password);
				ps.setBlob(10,photo);
				ps.setString(11, null);
				ps.setBlob(12, aadharcard);
				ps.setString(13, null);
				
				System.out.println("hello 0");
				PreparedStatement ps1=con.prepareStatement("insert into login values(?,?)");
				ps1.setString(1, username);
				ps1.setString(2, password);
				
				ps.executeUpdate();
				ps1.executeUpdate();
			
				System.out.println("hello 1");
				
				//SendMail s=new SendMail();
				//s.send(emailid, votingno);
				String apiKey="2C8rHqMLmiuae0A9TQfUZcPSBGnVY6tbsFE1y7IvxKwWkX5RpNQTpObsBlPND6ECWdFG8XSmyMAnz0UH";
				
				Otp p=new Otp();
				p.SendOtp(" Dear voter your Voting id is  "+ votingno, mobileno, apiKey);
				
				System.out.println("hello 2");
				out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
	            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
	            out.println("<script>");
	            out.println("$(document).ready(function(){");
	            out.println("swal('SUCCESS !', 'Congratulation..You are Successfully Registered', 'success');");
	            out.println("});");
	            out.println("</script>");
	           
	           
	            RequestDispatcher rd = request.getRequestDispatcher("HomePage.html");
	            rd.include(request, response);
				
			}
			catch(Exception p)
			{
				p.printStackTrace();
			}
		
	    

	}
}
