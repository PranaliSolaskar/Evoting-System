package ppp;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/AddPartyInfo")
public class AddPartyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddPartyInfo() {
        super();
       
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pt=response.getWriter();
		pt.println("Hello");
		
		
		HttpSession session=request.getSession();
		InputStream filename=null;
		String partyname=null; //request.getParameter("partyname");
		String presidentofparty=null ;//request.getParameter("partypresident");
		String totalmembers=request.getParameter("totalmembers");
		//filename=request.getParameter("partysymbol");
		PrintWriter out=response.getWriter();
		
		/*if(partyname.isEmpty()|| partyname==null || presidentofparty.isEmpty()|| totalmembers.isEmpty() || totalmembers==null )
		{
			pt.println("Hello 1");
			out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
            out.println("<script>");
            out.println("$(document).ready(function(){");
            out.println("swal('ERROR !', 'All Fields are manadatory....Try Again', 'error');");
            out.println("});");
            out.println("</script>");
            
        	
            
            
           RequestDispatcher rd = request.getRequestDispatcher("AddPartyInfo.html");
           rd.include(request, response);
           response.setHeader("Cache-Control", "private, no-store, no-cache,must-revalidate");
			 response.setHeader("Pragma", "no-cache");
			
		}
		else
		{*/
			
			try
			{
				DiskFileItemFactory factory=new DiskFileItemFactory();
				ServletFileUpload fileupload=new ServletFileUpload(factory);
				File image=new File("E:\\\\Photo");
				factory.setRepository(image);
				//File image=new File("E:\\\\Pranali");
				pt.println("Hello11");
				FileInputStream fis=null;
				
				
				List<FileItem> items=fileupload.parseRequest(request);
				for(FileItem item: items)
				{
					if(item.isFormField())
					{
						String fieldname=item.getFieldName();
						if(fieldname.equals("partypresident"))
						{
							presidentofparty=item.getString();
							
						}
						else if(fieldname.equals("partyname"))
						{
							partyname=item.getString();
						}
						else if(fieldname.equals("totalmembers"))
						{
							totalmembers=item.getString();
						}
						
					}
					if(item.getFieldName().equals("partysymbol"))
					{
						filename=item.getInputStream();
					}
				}
				
				
				
				//System.out.println("errotr1");
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
				//System.out.println("errotr2"+partyname);
				PreparedStatement pstmt=con.prepareStatement("insert into partyinfo values(?,?,?,?,?)");
				pstmt.setString(1, "202");
				pstmt.setString(2, partyname);
				pstmt.setString(3, presidentofparty);
				pstmt.setString(4, totalmembers);
				pstmt.setBlob(5,filename);
				//fis=new FileInputStream(image);
				//pstmt.setBinaryStream(4, (InputStream) fis, (int)(image.length()));
				int count=pstmt.executeUpdate();
				//System.out.println("errotr3");
				//Statement stmt=con.createStatement();
				//stmt.executeUpdate("insert into partyinfo values('"+partyname+"','"+presidentofparty+"','"+totalmembers+"','"+filename+"')");
				/*if(count>0)
				{
				out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
	            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
	            out.println("<script>");
	            out.println("$(document).ready(function(){");
	            out.println("swal('SUCCESS !', 'Congratulation..You are Successfully Registered', 'success');");
	            out.println("});");
	            out.println("</script>");
				}
	           
	            RequestDispatcher rd = request.getRequestDispatcher("HomePage.html");
	            rd.include(request, response);
	            response.setHeader("Cache-Control", "private, no-store, no-cache,must-revalidate");
				 response.setHeader("Pragma", "no-cache");*/
				
				 RequestDispatcher rd = request.getRequestDispatcher("/AdminDashboard.html");
		            rd.forward(request, response);
			}
			catch(Exception p)
			{
				p.printStackTrace();
			}
		//}
			
	}

}
