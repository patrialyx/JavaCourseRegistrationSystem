package stars;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class emailService implements NotificationInterface {
    private Student student;
    final String username = "cz20022020@gmail.com"; // to be added
    final String password = "ilovecz2002"; // to be added
    
    public emailService(Student student){
        this.student = student;
    }

    public void sendNotification(Index index) {
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
			message.setFrom(new InternetAddress("starsadmin@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(student.getEmail())); // to be added an email addr
			message.setSubject("Notification on Waiting List");
			message.setText("Dear " + student.getStudentName() + ","
				+ "\n\n Your application for " + index.getCourse().getCourseName() +", " + index.getIndexNumber() +  " has been successful!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
}