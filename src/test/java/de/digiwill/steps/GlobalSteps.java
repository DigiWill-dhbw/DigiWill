package de.digiwill.steps;

import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.util.SeleniumDriverUtils;
import org.junit.AfterClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    //@Before("@uitest")
    public void beforeUITest() {
        switch (System.getProperty("browser", "chrome")) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
                springBootBaseIntegrationTest.setWebDriver(new ChromeDriver(SeleniumDriverUtils.getChromeOptions()));
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", SeleniumDriverUtils.getFirefoxDriverPath());
                springBootBaseIntegrationTest.setWebDriver(new FirefoxDriver(SeleniumDriverUtils.getFirefoxOptions()));
                break;
            default:
                break;
        }
    }

    @After("@uitest")
    public void afterUITest() {
        //springBootBaseIntegrationTest.getWebDriver().close();
        springBootBaseIntegrationTest.getWebDriver().manage().deleteAllCookies();
        springBootBaseIntegrationTest.dropUsers();
    }
}
