package de.digiwill.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class FirefoxWebDriver extends EventFiringWebDriver {

    private static final WebDriver webdriver;

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webdriver.close();
        }
    };

    static {
        System.setProperty("webdriver.gecko.driver", SeleniumDriverUtils.getFirefoxDriverPath());

        FirefoxOptions capabilities = SeleniumDriverUtils.getFirefoxOptions();
        webdriver = new FirefoxDriver(capabilities);
        webdriver.manage().window().maximize();

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public FirefoxWebDriver() {
        super(webdriver);
    }

}
