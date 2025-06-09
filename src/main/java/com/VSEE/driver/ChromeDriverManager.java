package com.VSEE.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        WebDriverManager.chromedriver().setup();
        String language = "en_GB";
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> chrome_prefs = new HashMap<>();
        chrome_prefs.put("intl.accept_languages", language);
        chrome_prefs.put("profile.default_content_setting_values.geolocation", 2);
        options.setExperimentalOption("prefs", chrome_prefs);
        String driver_path = System.getProperty("user.dir") + File.separator + "driver" + File.separator + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver = new ChromeDriver();
    }
}
