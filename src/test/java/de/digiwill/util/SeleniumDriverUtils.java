package de.digiwill.util;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumDriverUtils {

    private static String CHROME_DRIVER_WINDOWS = ".\\\\driver\\\\chromedriver.exe";
    private static String CHROME_DRIVER_MAC_OSX = "./driver/chromedriver_osx";
    private static String CHROME_DRIVER_LINUX = "/usr/bin/chromedriver";

    private static String FIREFOX_DRIVER_WINDOWS = ".\\\\driver\\\\geckodriver.exe";
    private static String FIREFOX_DRIVER_LINUX = "/usr/bin/geckodriver";

    public static String getChromeDriverPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return CHROME_DRIVER_WINDOWS;
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            return CHROME_DRIVER_MAC_OSX;
        } else if (SystemUtils.IS_OS_LINUX) {
            return CHROME_DRIVER_LINUX;
        }
        return "";
    }

    public static String getFirefoxDriverPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return FIREFOX_DRIVER_WINDOWS;
        } else if (SystemUtils.IS_OS_LINUX) {
            return FIREFOX_DRIVER_LINUX;
        }
        return "";
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        if (SystemUtils.IS_OS_LINUX) {
            options.addArguments("--headless");
        }
        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        if (SystemUtils.IS_OS_LINUX) {
            options.addArguments("--headless");
        }
        options.addArguments("--headless");
        return options;
    }
}

