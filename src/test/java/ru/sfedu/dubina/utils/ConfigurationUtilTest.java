package ru.sfedu.dubina.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationUtilTest {

    @Test
    void getConfigFilePath() {
        System.out.println(ConfigurationUtil.getConfigFilePath());
    }
}