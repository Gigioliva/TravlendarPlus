package servlet;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class contains the logic for sending the registration confirmation
 * e-mail
 */
public class Mail {

	public static void inviaMail(String to) {
		// Sender's email ID needs to be mentioned
		String from = "travlendarmom94@gmail.com";

		// Assuming you are sending email from localhost
		String host = "aspmx.l.google.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Registrazione Travlendar");

			// Now set the actual message
			message.setText("Congratulazioni hai appena completato la registrazione a Travlendar!!!");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
