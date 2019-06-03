package de.digiwill.steps;

import com.icegreen.greenmail.util.GreenMail;
import cucumber.api.java.en.And;
import de.digiwill.model.UserHandle;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.service.UserHandleManager;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.internet.MimeMessage;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class RegisterSteps {
    @Autowired
    private GreenMail greenMail;
    @Autowired
    private WebDriver webDriver;
    @Autowired
    private UserHandleManager userHandleManager;

    @And("I open verification mail with account {string}")
    public void iEnterIntoFieldWithId(String email) throws Exception {
        if (greenMail.waitForIncomingEmail(5000, 1)) {
            //check if user is not verified
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(email);
            assertEquals(false, userHandle.isVerified());

            MimeMessage[] messages = greenMail.getReceivedMessages();
            assertEquals(1, messages.length);
            MimeMessage message = messages[0];
            assertEquals(EmailDispatcher.REGISTRATION_EMAIL_SUBJECT, message.getSubject());
            String content = (String) message.getContent();
            webDriver.get(content.split("\"")[1]);

            //check if user is verified
            userHandle = userHandleManager.loadUserByEmailAddress(email);
            assertEquals(true, userHandle.isVerified());

        } else {
            fail("No registration mail received!");
        }
    }

}
