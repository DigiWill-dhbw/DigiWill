package de.digiwill.maildispatcher;

import de.digiwill.exception.EmailException;
import de.digiwill.model.*;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.util.EmailTransportWrapper;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailDispatcherTest {

    private EmailDispatcher emailDispatcher;
    @Mock
    private EmailTransportWrapper emailTransportWrapper;

    private ArgumentCaptor<Message> sentMessage;
    private String subject = "subject";
    private String content = "content";

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.google.com");
        emailDispatcher = new EmailDispatcher(Session.getInstance(properties), emailTransportWrapper, "http://localhost:8080/");
        sentMessage = ArgumentCaptor.forClass(Message.class);
        reset(emailTransportWrapper);
    }

    public void compareMessage(ArgumentCaptor<Message> m, String[] recipients, String subject, String content) throws IOException, MessagingException {
        InternetAddress[] mailAddresses = InternetAddress.parse(String.join(",", recipients), true);
        Assert.assertArrayEquals(mailAddresses, m.getValue().getAllRecipients());
        Assert.assertEquals(subject, m.getValue().getSubject());
        Assert.assertEquals(content, m.getValue().getContent());
    }

    @Test
    public void sendEmailTest() throws Exception {
        String[] recipient = {"test@testmail.com"};

        emailDispatcher.sendEmail(recipient[0], subject, true, content);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        compareMessage(sentMessage, recipient, subject, content);
    }

    @Test
    public void endEmailListTest() throws Exception {
        List<String> recipients = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            recipients.add("test" + i + "@test.de");
        }
        emailDispatcher.sendEmail(recipients, subject, true, content);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        compareMessage(sentMessage, recipients.toArray(new String[recipients.size()]), subject, content);
    }

    @Test(expected = EmailException.class)
    public void sendEmailTestMissingRecipients() throws Exception {
        emailDispatcher.sendEmail("", subject, true, content);
    }

    @Test(expected = EmailException.class)
    public void sendMailMalformedEmailAddress() throws Exception {
        emailDispatcher.sendEmail("jfdsklfjsl", subject, true, content);
    }


    @Test
    public void sendRegistrationConfirmationEmailTest() throws Exception {
        PersonalData personalData = new PersonalData("TestFirstName", "TestSurname", null);
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthoritiesPersonalData("user@name.de", "test", personalData, null);
        userHandle.setVerified(false);
        EmailResponseHandle emailResponseHandle = new EmailVerificationHandle(new ObjectId("abcdefabcdefabcdefabcdef"), userHandle.getEmailAddress() , "token" , 10 );
        String[] recipient = {userHandle.getEmailAddress()};

        emailDispatcher.sendRegistrationConfirmationEmail(emailResponseHandle, userHandle);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        compareMessage(sentMessage, recipient, EmailDispatcher.REGISTRATION_EMAIL_SUBJECT,
                EmailDispatcher.REGISTRATION_EMAIL_CONTENT.replace("<firstName>", userHandle.getPersonalData().getFirstName())
                        .replace("<url>", "http://localhost:8080/" + emailResponseHandle.getLinkSuffix()));

    }

    @Test
    public void sendResetEmailTest() throws Exception {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("user@name.de", "test", null);
        EmailResponseHandle emailResponseHandle = new EmailResetHandle(new ObjectId("abcdefabcdefabcdefabcdef"), userHandle.getEmailAddress() , "token" , 10);
        String[] recipient = {userHandle.getUsername()};

        emailDispatcher.sendResetEmail(emailResponseHandle, userHandle);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        compareMessage(sentMessage, recipient, EmailDispatcher.RESET_EMAIL_SUBJECT,
                EmailDispatcher.RESET_EMAIL_CONTENT.replace("<firstName>", userHandle.getPersonalData().getFirstName())
                        .replace("<url>", "http://localhost:8080/" + emailResponseHandle.getLinkSuffix()));
    }

    @Test
    public void sendReminderEmailTest() throws Exception {
        PersonalData personalData = new PersonalData("TestFirstName", "TestSurname", null);
        UserHandle uh = UserHandleFactory.createUserHandleWithEmailPasswordAuthoritiesPersonalData("user@name.de", "test", personalData, null);
        String[] recipient = {uh.getEmailAddress()};

        emailDispatcher.sendReminderEmail(uh);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        compareMessage(sentMessage, recipient, EmailDispatcher.REMINDER_EMAIL_SUBJECT,
                EmailDispatcher.REMINDER_EMAIL_CONTENT.replaceAll("<firstName>", uh.getPersonalData().getFirstName())
                        .replace("<url>", "http://localhost:8080/"));

    }


}
