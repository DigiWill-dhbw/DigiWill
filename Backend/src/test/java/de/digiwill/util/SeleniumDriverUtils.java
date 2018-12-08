package de.digiwill.util;

import org.apache.commons.lang3.SystemUtils;

public class SeleniumDriverUtils {

    private static String CHROME_DRIVER_WINDOWS = "..\\Backend\\\\driver\\\\chromedriver.exe";
    private static String CHROME_DRIVER_MAC_OSX = "../Backend/driver/chromedriver_osx";
    private static String CHROME_DRIVER_LINUX = "/usr/bin/chromedriver";

    public static String getChromeDriverPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return CHROME_DRIVER_WINDOWS;
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            return CHROME_DRIVER_MAC_OSX;
        } else if (SystemUtils.IS_OS_LINUX) {
            return CHROME_DRIVER_LINUX;
        } else {
            return "";
        }
    }

}

