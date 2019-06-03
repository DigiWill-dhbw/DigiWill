package de.digiwill.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

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
        webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public FirefoxWebDriver() {
        super(webdriver);
    }

}
