package de.digiwill.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.util.SeleniumDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class GlobalSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Before("@uitest")
    public void beforeUITest() {
        switch (System.getProperty("browser", "chrome")) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
                springBootBaseIntegrationTest.setWebDriver(new ChromeDriver(SeleniumDriverUtils.getChromeOptions()));
            case "firefox":
                System.setProperty("webdriver.gecko.driver", SeleniumDriverUtils.getFirefoxDriverPath());
                springBootBaseIntegrationTest.setWebDriver(new FirefoxDriver(SeleniumDriverUtils.getFirefoxOptions()));
                break;
            case "edge":
                //TODO add edge
                break;
        }
    }

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().quit();
        springBootBaseIntegrationTest.dropUsers();
    }

    private void settingImplicitWait(WebDriver driver) {
        //setting implicit wait for driver
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }
}
