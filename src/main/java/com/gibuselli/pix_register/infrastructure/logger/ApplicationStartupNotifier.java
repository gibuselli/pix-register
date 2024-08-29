package com.gibuselli.pix_register.infrastructure.logger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupNotifier implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupNotifier.class);
    private static final String swaggerPath = "/swagger-ui/index.html";
    private static final String contextPath = "server.servlet.context-path";
    private static final String appNameProperty = "spring.application.name";
    private final Environment environment;

    public ApplicationStartupNotifier(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String appName = environment.getProperty(appNameProperty, "app");

        String[] activeProfiles = environment.getActiveProfiles();
        String profiles = String.join(", ", activeProfiles.length > 0 ? activeProfiles : new String[]{"default"});

        String appPath = environment.getProperty(contextPath, StringUtils.EMPTY);

        String swaggerUrl = appPath + swaggerPath;

        logger.info("Active profile: {}", profiles);
        logger.info("Swagger URL: http://localhost:{}{}", environment.getProperty("server.port", "8080"), swaggerUrl);
        logger.info("Application {} is running!", appName.toUpperCase());
    }
}
