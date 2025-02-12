package com.restassured.util;

import com.restassured.constant.CommonConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.restassured.constant.ReporterConstant.TEST_REPORTER_PROPERTY_FILE_DIRECTORY;

public class FileReader {
    private static final Logger logger = LogManager.getLogger();

    public static String getEnvironmentConfig(String propertyName) {
        String fileName = "./src/test/resources/env/" + CommonConstant.EXECUTION_ENV_NAME + ".properties";
        Path filePath = Paths.get(fileName);
        return getPropertyFromFile(filePath, propertyName);
    }

    public static String getTestReporterProperty(String propertyName) {
        return getPropertyFromFile(TEST_REPORTER_PROPERTY_FILE_DIRECTORY, propertyName);
    }

    private static String getPropertyFromFile(Path filePath, String propertyName) {
        Properties properties = loadProperties(filePath);
        return properties.getProperty(propertyName);
    }
    private static Properties loadProperties(Path filePath) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties from file: {}", filePath);
            logger.error(e.getMessage());
        }
        return properties;
    }
}
