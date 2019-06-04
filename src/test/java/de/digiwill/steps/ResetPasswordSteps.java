package de.digiwill.steps;

import com.icegreen.greenmail.util.GreenMail;
import cucumber.api.java.en.And;
import de.digiwill.model.EmailResponseHandle;
import de.digiwill.model.UserHandle;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.internet.MimeMessage;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ResetPasswordSteps {

    @Autowired
    private GreenMail greenMail;
    @Autowired
    private WebDriver webDriver;
    @Autowired
    private EmailResponseHandleManager emailResponseHandleManager;

    @And("I open password recovery mail with account {string}")
    public void iEnterIntoFieldWithId(String email) throws Exception {
        if (greenMail.waitForIncomingEmail(5000, 1)) {
            MimeMessage[] messages = greenMail.getReceivedMessages();
            assertEquals(1, messages.length);
            MimeMessage message = messages[0];
            assertEquals(EmailDispatcher.RESET_EMAIL_SUBJECT, message.getSubject());
            String content = (String) message.getContent();
            webDriver.get(content.split("\"")[1]);

        } else {
            fail("No password recovery mail received!");
        }
    }

}
