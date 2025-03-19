package ru.sfedu.dubina.utils;

public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "mavenproject\\src\\main\\resources\\hibernate.cfg.xml";


    public static String getConfigFilePath() {
        String configFilePath = System.getProperty("hibernate.config.path");
        if (configFilePath == null || configFilePath.isEmpty()) {
            configFilePath = DEFAULT_CONFIG_PATH;
        }
        return configFilePath;
    }
}
