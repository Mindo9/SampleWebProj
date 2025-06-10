package com.VSEE.driver;

import com.VSEE.log.LogHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager extends DriverManager {
    private static Logger logger = LogHelper.getLogger();
    @Override
    protected void createDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = getChromeOptions();
            driver = new ChromeDriver(options);
        } catch (Exception e) {
            logger.info("⚠️ Auto setup WebDriverManager Chrome failed, using fallback driver.");
            String driver_path = System.getProperty("user.dir") + File.separator + "driver" + File.separator + "chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", driver_path);
            ChromeOptions options = getChromeOptions();
            driver = new ChromeDriver(options);
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> chrome_prefs = new HashMap<>();
        chrome_prefs.put("intl.accept_languages", "en_GB");
        chrome_prefs.put("profile.default_content_setting_values.geolocation", 2);
        chrome_prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        chrome_prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        chrome_prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", chrome_prefs);
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");
        return options;
    }

}
