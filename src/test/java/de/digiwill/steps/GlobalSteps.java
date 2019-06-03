package de.digiwill.steps;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import cucumber.api.java.After;
import de.digiwill.SpringBootBaseIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Autowired
    private GreenMail greenMail;

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().manage().deleteAllCookies();
        springBootBaseIntegrationTest.dropUsers();
    }

    @After("@mailtest")
    public void afterMailTest() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }
}
