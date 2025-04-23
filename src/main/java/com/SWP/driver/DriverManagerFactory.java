package com.SWP.driver;

import com.SWP.enums.DriverType;

public class DriverManagerFactory {

    public static DriverManager getManager(DriverType type) {
        DriverManager driverManager = null;
        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
            case EDGE:
                driverManager = new MicrosoftEdgeDriverManager();
            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;


    }

}
