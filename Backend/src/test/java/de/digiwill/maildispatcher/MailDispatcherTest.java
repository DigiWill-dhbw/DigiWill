package de.digiwill.maildispatcher;

import de.digiwill.configuration.Config;
import de.digiwill.util.EmailDispatcher;
import de.digiwill.util.EmailTransportWrapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;

import javax.mail.Message;
import javax.mail.Session;

import java.util.Properties;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailDispatcherTest {

    EmailDispatcher emailDispatcher;
    Session session;
    @Mock EmailTransportWrapper emailTransportWrapper;

    ArgumentCaptor<Message> sentMessage;

    @BeforeClass
    public void classSetup(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.google.com");
        this.session = Session.getInstance(properties);
        this.emailDispatcher = new EmailDispatcher(session, emailTransportWrapper);
        this.sentMessage = ArgumentCaptor.forClass(Message.class);
    }


    @After
    public void teardownMethod(){
        reset(emailTransportWrapper);
    }

    @Test
    public void sendEmailTest() throws Exception{
        String content = "content";
        emailDispatcher.sendEmail("test", "subject", true, content);
        verify(emailTransportWrapper).sendMessage(sentMessage.capture());
        Assert.assertEquals(content, sentMessage.getValue().getContent());
    }

    public void sendRegistrationConfirmationEmailTest(){
        //emailDispatcher.sendRegistrationConfirmationEmail();
    }

}
