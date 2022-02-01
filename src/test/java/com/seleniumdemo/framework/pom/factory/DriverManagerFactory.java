package com.seleniumdemo.framework.pom.factory;

import com.seleniumdemo.framework.pom.constants.DriverType;

public class DriverManagerFactory {
    public static DriverManager getDriverManager(DriverType driverType){
        switch (driverType){
            case CHROME:
                return new ChromeDriverManager();
            case FIREFOX:
                return new FirefoxDriverManager();
            case SAFARI:
                return new SafariDriverManager();
            default:
                throw new IllegalStateException("Invalid driver type " + driverType);
        }
    }
}
