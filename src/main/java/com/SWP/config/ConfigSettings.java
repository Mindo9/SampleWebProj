package com.SWP.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigSettings {

    private static ConfigSettings config;

    private static final String BROWSER = "browser";
    private static final String DEFAULT_TIMEOUT = "timeout";

    private Properties configProperties;

    public ConfigSettings(String projectDirLocator) {
        try {
            setConfigProperties(PropertySettingStoreUtil.getSettings(projectDirLocator, ConfigSettings.class.getSimpleName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getConfigProperties() {
        if(configProperties == null) {
            configProperties = new Properties();
        }
        return configProperties;
    }

    public String getBrowser() {
        return this.configProperties.getProperty(BROWSER);
    }

    public String getDefaultTimeout() {
        return this.configProperties.getProperty(DEFAULT_TIMEOUT);
    }

    public void setConfigProperties(Properties configProperties) {
        this.configProperties = configProperties;
    }

}
