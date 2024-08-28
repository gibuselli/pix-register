package com.gibuselli.pix_register.infrastructure.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CustomLogger {

    private final Logger logger;
    private final Map<String, String> tags = new HashMap<>();

    private CustomLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(clazz);
    }

    public CustomLogger tag(String key, String value) {
        tags.put(key, value);
        return this;
    }

    private String formatMessage(String message) {
        if (tags.isEmpty()) {
            return message;
        }
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        tags.forEach((key, value) -> joiner.add(key + "=" + value));
        return joiner.toString() + " " + message;
    }

    public void info(String message) {
        logger.info(formatMessage(message));
        tags.clear();
    }

    public void warn(String message) {
        logger.warn(formatMessage(message));
        tags.clear();
    }

    public void error(String message) {
        logger.error(formatMessage(message));
        tags.clear();
    }

    public void debug(String message) {
        logger.debug(formatMessage(message));
        tags.clear();
    }
}
