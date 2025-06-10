package com.VSEE.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MicrosoftEdgeDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        String driver_path = System.getProperty("user.dir") + File.separator + "driver" + File.separator + "msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", driver_path);
        EdgeOptions options = getEdgeOptions();
        driver = new EdgeDriver(options);

    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> edge_prefs = new HashMap<>();
        edge_prefs.put("intl.accept_languages", "en_GB");
        edge_prefs.put("profile.default_content_setting_values.geolocation", 2);
        edge_prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        edge_prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        edge_prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", edge_prefs);
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");
        return options;
    }
}
