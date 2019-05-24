package de.digiwill.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class ChromeWebDriver extends EventFiringWebDriver {

    private static final WebDriver webdriver;

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webdriver.close();
        }
    };

    static {
        System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());

        ChromeOptions capabilities = SeleniumDriverUtils.getChromeOptions();
        webdriver = new ChromeDriver(capabilities);
        webdriver.manage().window().maximize();

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public ChromeWebDriver() {
        super(webdriver);
    }

}
