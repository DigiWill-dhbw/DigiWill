package de.digiwill.steps;

import com.icegreen.greenmail.store.FolderException;
import cucumber.api.java.After;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.config.GreenMailBean;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Autowired
    private GreenMailBean greenMailBean;

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().manage().deleteAllCookies();
        springBootBaseIntegrationTest.dropUsers();
    }

    @After("@mailtest")
    public void afterMailTest() throws FolderException {
        greenMailBean.getGreenMail().purgeEmailFromAllMailboxes();
    }
}
