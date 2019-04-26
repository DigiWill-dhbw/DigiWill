package de.digiwill.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.util.SeleniumDriverUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalSteps {


    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Before("@uitest")
    public void beforeUITest() {
        System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
        springBootBaseIntegrationTest.setWebDriver(new ChromeDriver(SeleniumDriverUtils.getChromeOptions()));
    }

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().quit();
        springBootBaseIntegrationTest.dropUsers();
    }
}
