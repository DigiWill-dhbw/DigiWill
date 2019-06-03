package de.digiwill.util;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

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
        capabilities.setPageLoadStrategy(PageLoadStrategy.EAGER);
        webdriver = new ChromeDriver(capabilities);
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public ChromeWebDriver() {
        super(webdriver);
    }

}
