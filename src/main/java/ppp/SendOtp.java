package ppp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class SendOtp {
	public static void send(String to,int otp) 
	{
		Properties props=new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.auth","true");
		//props.put("mail.smtp.starttls.enable", "true");
	//	PrintWriter pt=response.getWriter();
		//pt.println("Hello");
		System.out.println("hello1");
		 Session session=Session.getInstance(props, new javax.mail.Authenticator()
				 {
			 		protected PasswordAuthentication getPasswordAuthentication()
			 		{
			 			return new PasswordAuthentication("EMAILID@gmail.com","PASSWORD");
			 		}
				 });
		 session.getProperties().put("mail.smtp.starttls.enable", "true");
	//	 session.getProperties().put("mail.smtp.port", "587");
		 session.getProperties().put("mail.smtp.ssl.trust", "*");
			
		 System.out.println("hello2"+otp);
		 try
		 {
			 MimeMessage message=new MimeMessage(session);
			 message.setFrom(new InternetAddress("pranaleesolaskar17@gmail.com"));
			 message.addRecipient(RecipientType.TO, new InternetAddress(to));
			 message.setText("Your One time Password is "+otp+".Please Enter following OTP To Login into Evoting Portal. ");
	         message.setSubject("Evoting System");
	         System.out.println("hello3");
	         Transport.send(message);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

	}

}

