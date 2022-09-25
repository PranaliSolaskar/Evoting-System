package ppp;

import java.io.IOException;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/AddCandidate")
public class AddCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddCandidate() {
        super();
       
    }

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pt=response.getWriter();
		pt.println("Hello");
		
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		String candidatename=null;
		String partyname=null;
		
		pt.println("Hello 2");
		
		InputStream candidatephoto=null;
		InputStream partysymbol=null;
		boolean isMultiPartData=ServletFileUpload.isMultipartContent(request);
		
		pt.println("Hello 3");
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
							if(fieldname.equals("candidatename"))
							{
								candidatename=item.getString();
								
							}
							else if(fieldname.equals("partyname"))
							{
								partyname=item.getString();
							}
							
						}
						else
						{
							if(item.getFieldName().equals("candidatephoto"))
							{
								candidatephoto=item.getInputStream();
							}
							if(item.getFieldName().equals("partysymbol"))
							{
								partysymbol= item.getInputStream();
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
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
			pt.println("Hello 4");
			PreparedStatement ps=con.prepareStatement("insert into candidates values(?,?,?,?,?)");
			ps.setString(1, "112");
			ps.setString(2, candidatename);
			ps.setString(3, partyname);
			ps.setBlob(4,candidatephoto);
			ps.setBlob(5, partysymbol);
			
			ps.executeUpdate();
			
			pt.println("Hello 6");
			
		//	JOptionPane.showMessageDialog(null, "Congratulations !!","Your Data is Succesfully Added ",JOptionPane.INFORMATION_MESSAGE);
			out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
            out.println("<script>");
            out.println("$(document).ready(function(){");
            out.println("swal('SUCCESS !', 'Congratulation..Candidate Added Successfully', 'success');");
            out.println("});");
            out.println("</script>"); 
           
           
            RequestDispatcher rd = request.getRequestDispatcher("/AdminDashboard.html");
            rd.forward(request, response);
        //    response.setHeader("Cache-Control", "private, no-store, no-cache,must-revalidate");
		//	 response.setHeader("Pragma", "no-cache");
			
		}
		catch(Exception p)
		{
			p.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

}
