package org.jbpm.demo.common.test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jbpm.demo.common.util.EncryptionUtil;

public class MailSendTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final String username = getUser();
		final String password = getPassword();
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ksoong@redhat.com"));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("ksoong@redhat.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private static String getUser() {
		try {
			return EncryptionUtil.doDecryption("mGj4ruUOLlyL876CfOL6GwEjb5JLJV5bFMc5PwHiFv0=");
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}

	private static String getPassword() {
		try {
			return EncryptionUtil.doDecryption("ZoCWqU/8hpCZW/cfK2Y6sg==");
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}

}
