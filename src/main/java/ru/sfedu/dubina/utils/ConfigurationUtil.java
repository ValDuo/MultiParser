package ru.sfedu.dubina.utils;


public class ConfigurationUtil {

    public static String getConfigFilePath() {
        String configFilePath = System.getProperty("hibernate.config.path");
        if (configFilePath == null || configFilePath.isEmpty()) {
            configFilePath = Constants.DEFAULT_CONFIG_PATH;
        }
        return configFilePath;
    }
}
