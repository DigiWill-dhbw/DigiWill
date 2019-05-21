package de.digiwill.service;

import de.digiwill.exception.EmailException;
import de.digiwill.model.EmailResponseHandle;
import de.digiwill.model.UserHandle;
import de.digiwill.util.EmailTransportWrapper;
import de.digiwill.util.RegexMatcher;
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

    public static final String RESET_EMAIL_SUBJECT = "";
    public static final String RESET_EMAIL_CONTENT = "";
    public static final String REGISTRATION_EMAIL_SUBJECT = "Confirm your registration!";
    public static final String REGISTRATION_EMAIL_CONTENT = "Hello <firstName><br/><br/>" +
            "please confirm your registration by clicking <a href=\"<url>\">this link</a>.<br/>" +
            "Thanks for using our service<br/><br/>" +
            "Regards, <br/>DigiWill";
    public static final String REMINDER_EMAIL_SUBJECT = "Are you dead?";
    public static final String REMINDER_EMAIL_CONTENT = "Hello <firstName>,<br/>" +
            "we have noticed you haven't checked in with us for a long time.<br/>" +
            "Please confirm that you are alive in your app or at <a href=\"<url>\">this website</a>.<br/><br/>" +
            "Regards, <br/>DigiWill";

    private final Logger logger = LoggerFactory.getLogger(EmailDispatcher.class);
    private final Session session;
    private final EmailTransportWrapper emailTransportWrapper;
    private String callbackUrl;

    public EmailDispatcher(Session session, EmailTransportWrapper emailTransportWrapper, String callbackUrl) {
        this.session = session;
        this.emailTransportWrapper = emailTransportWrapper;
        this.callbackUrl = callbackUrl;

    }

    //TODO refactor Registration and Reset Email into a single method for system emails
    public void sendRegistrationConfirmationEmail(EmailResponseHandle responseHandle, UserHandle userHandle) throws EmailException {
        logger.debug("Initiating sendRegistrationConfirmationEmail");
        String content = REGISTRATION_EMAIL_CONTENT.replace("<firstName>", userHandle.getPersonalData().getFirstName())
                .replace("<url>", callbackUrl + responseHandle.getLinkSuffix());
        try {
            sendEmail(userHandle.getEmailAddress(), REGISTRATION_EMAIL_SUBJECT, true, content);
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
        logger.debug("Initiating sendReminder");
        String content = REMINDER_EMAIL_CONTENT.replaceAll("<firstName>", userHandle.getPersonalData().getFirstName())
                .replace("<url>", callbackUrl );
        //TODO generate Link for user and refactor message content into file

        try {
            sendEmail(userHandle.getEmailAddress(), REMINDER_EMAIL_SUBJECT, true, content);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reminder", e);
        }
    }

    public void sendEmail(List<String> recipients, String subject, boolean htmlContentFlag, String content) throws EmailException {
        sendEmail(String.join(",", recipients), subject, htmlContentFlag, content);
    }

    public void sendEmail(String recipient, String subject, boolean htmlContentFlag, String content) throws EmailException {
        logger.debug("Creating Email");
        if (RegexMatcher.isValidMultipleEmailAddress(recipient)) {
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(session.getProperty("mail.smtp.from"), "DigiWill"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, true));
                message.setSubject(subject);
                message.setContent(content, htmlContentFlag ? MediaType.TEXT_HTML_VALUE : MediaType.TEXT_PLAIN_VALUE);
                message.setSentDate(new Date());
            } catch (Exception e) {
                logger.error("Error creating mail.");
                throw new EmailException(e.getMessage(), e);
            }
            logger.debug("Sending Email");
            try {
                emailTransportWrapper.sendMessage(message);
            } catch (MessagingException e) {
                logger.error("Error sending mail.");
                throw new EmailException(e.getMessage());
            }

            logger.debug("Email sent");
        } else {
            logger.error("Recipient email address is invalid: " + recipient);
            throw new EmailException("Recipient email address is invalid: " + recipient);
        }
    }

}

