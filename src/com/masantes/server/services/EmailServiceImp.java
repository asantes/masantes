package com.masantes.server.services;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.masantes.client.services.EmailService;
import com.masantes.shared.model.ContactForm;

public class EmailServiceImp extends RemoteServiceServlet implements EmailService
{
	private static final long serialVersionUID = 3124363574341680515L;
	public static final Logger log = Logger.getLogger(EmailService.class.getName());
	static final String FROM = "nonostcc@gmail.com";
	static final String FROM_NAME = "Maria Sanchez Teston Web";
	static final String TO = "nonostcc@gmail.com";
	static final String TO_NAME = "Maria";
	static final String SUBJECT = "Nuevo mensaje recibido de ";
	
	public EmailServiceImp() {
	}

	@Override
	public void sendEmail(ContactForm contactForm) 
	{
		boolean ok = false;
		String name = contactForm.getName();
		String email = contactForm.getEmail();
		String text =  contactForm.getMessage();
		String subject = SUBJECT+'"'+name+'"'+'('+email+')';
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props,null);
		
		log.info(text);
		
		MimeMessage message = new MimeMessage(session);
		try 
		{
			/* Set Sender & Receiver */
			InternetAddress from = new InternetAddress(FROM, FROM_NAME);
			InternetAddress to = new InternetAddress(TO, TO_NAME);
			
			message.setSender(from);
			message.addRecipient(Message.RecipientType.TO, to);
			message.setSubject(subject, "UTF-8");
		
			/* Multipart content in order to send html */
			Multipart multipart = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			
			mbp.setContent(subject, "text/html");
			multipart.addBodyPart(mbp);
			message.setContent(multipart);

			/* Send email */
			Transport.send(message);
			ok = true;
		} 
		catch (AddressException e)	{
			log.log(Level.WARNING, e.getMessage());
			ok = false;
		}
		catch (UnsupportedEncodingException e) {
			log.log(Level.WARNING, e.getMessage());
			ok = false;
		} 
		catch (MessagingException e) {
			log.log(Level.WARNING, e.getMessage());
			ok = false;
		}
		
		if(ok)
			log.info("Nuevo mensaje recibido de "+name+" "+email);
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
