package digiwill;

import org.springframework.http.MediaType;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailDispatcher {
    public void sendRegistrationConfirmationEmail(EmailResponseHandle responseHandle) throws EmailException {//TODO implement

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send registration Email", e);
        }
    }

    public void sendResetEmail(EmailResponseHandle responseHandle) throws EmailException {//TODO implement

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reset Email", e);
        }
    }

    public void sendReminderEmail(User user) throws EmailException {//TODO implement

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reminder", e);
        }
    }

    public void sendEmail(List<String> recipients, String subject, boolean htmlContentFlag, String content) throws EmailException {//TODO implement
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", System.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", System.getProperty("mail.smtp.port"));

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getProperty("mail.adress"), System.getProperty("mail.password"));
            }
        });
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(System.getProperty("mail.adress"),false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", recipients), true));
            msg.setSubject(subject);
            msg.setContent(content, htmlContentFlag ? MediaType.TEXT_HTML_VALUE:MediaType.TEXT_PLAIN_VALUE);
            msg.setSentDate(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Transport.send(msg);
        } catch (MessagingException e) {
            System.err.println("Error sending Email.");
            e.printStackTrace();
        }

    }

}
