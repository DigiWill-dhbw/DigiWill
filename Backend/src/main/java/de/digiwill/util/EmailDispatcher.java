package de.digiwill.util;

import de.digiwill.exception.EmailException;
import de.digiwill.model.UserHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

public class EmailDispatcher {

    private Logger logger = LoggerFactory.getLogger(EmailDispatcher.class);
    private Session session;
    private EmailTransportWrapper emailTransportWrapper;

    public EmailDispatcher(Session session, EmailTransportWrapper emailTransportWrapper) {
        this.session = session;
        this.emailTransportWrapper = emailTransportWrapper;

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
        logger.debug("Creating Email");
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(session.getProperty("mail.smtp.host"),false));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, true));
            message.setSubject(subject);
            message.setContent(content, htmlContentFlag ? MediaType.TEXT_HTML_VALUE:MediaType.TEXT_PLAIN_VALUE);
            message.setSentDate(new Date());
        } catch (Exception e) {
            logger.error("Error creating mail.");
            e.printStackTrace();
            throw new EmailException(e.getMessage());
        }
        logger.debug("Sending Email");
        try {
            emailTransportWrapper.sendMessage(message);
        } catch (MessagingException e) {
            logger.error("Error sending mail.");
            e.printStackTrace();
            throw new EmailException(e.getMessage());
        }

        logger.debug("Email sent");
    }

}

