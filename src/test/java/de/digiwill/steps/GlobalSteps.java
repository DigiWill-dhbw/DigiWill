package de.digiwill.steps;

import cucumber.api.java.After;
import de.digiwill.SpringBootBaseIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().manage().deleteAllCookies();
        springBootBaseIntegrationTest.dropUsers();
    }
}
