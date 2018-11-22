package digiwill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailDispatcher {

    private Logger logger = LoggerFactory.getLogger(EmailDispatcher.class);
    private final EmailConfig emailconfig;
    private Session session;
    public EmailDispatcher(EmailConfig emailconfig) {
        this.emailconfig = emailconfig;
        logger.debug(emailconfig.getHost()+emailconfig.getPort()+emailconfig.getUser()+emailconfig.getPassword());
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailconfig.getHost());
        props.put("mail.smtp.port", emailconfig.getPort());

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailconfig.getUser(), emailconfig.getPassword());
            }
        });
    }
    public void sendRegistrationConfirmationEmail(EmailResponseHandle responseHandle) throws EmailException {
        logger.debug("Sending registration email.");
        String subject = "Confirm your registration!";
        String content = "Hello "+responseHandle.getUserHandle().getAlias()+"<br/><br/>"+
                "please confirm your registration by clicking <a href=\"https://registrierung.com\">this link</a>.<br/>"+
                "Thanks for using our service<br/><br/>"+
                "Regards, <br/>DigiWill";

        try {
            sendEmail(responseHandle.getUserHandle().getEmailAddress() , subject, true, content);
        } catch (EmailException e) {
            throw new EmailException("Failed to send registration Email", e);
        }
    }

    public void sendResetEmail(EmailResponseHandle responseHandle) throws EmailException {
        //TODO irgendwo irgendwie irgendwann

        try {
            sendEmail("", null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reset Email", e);
        }
    }

    public void sendReminderEmail(UserHandle userHandle) throws EmailException {
        logger.debug("Sending Reminder");
        String subject = "Are you dead?";
        String content = "Hello "+userHandle.getAlias()+",<br/>"+
                "we have noticed you haven't checked in with us for a long time.<br/>"+
                "Please confirm that you are alive in your app or at <a href=\"https://google.de\">this website</a>.<br/><br/>"+
                "Regards, <br/>DigiWill";
        //TODO generate Link for user and refactor message content into file

        try {
            sendEmail(userHandle.getEmailAddress() , subject, true, content);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reminder", e);
        }
    }

    public void sendEmail(List<String> recipients, String subject, boolean htmlContentFlag, String content) throws EmailException {
        sendEmail(String.join(",", recipients), subject, htmlContentFlag, content);
    }
    public void sendEmail(String recipient, String subject, boolean htmlContentFlag, String content) throws EmailException {
        logger.debug("Sending Email");
        Message msg = new MimeMessage(this.session);
        try {
            msg.setFrom(new InternetAddress(emailconfig.getUser(),false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, true));
            msg.setSubject(subject);
            msg.setContent(content, htmlContentFlag ? MediaType.TEXT_HTML_VALUE:MediaType.TEXT_PLAIN_VALUE);
            msg.setSentDate(new Date());
        } catch (Exception e) {
            logger.error("Error creating mail.");
            e.printStackTrace();
        }

        try {
            Transport.send(msg);
        } catch (MessagingException e) {
            logger.error("Error sending mail.");
            e.printStackTrace();
        }

    }

}

