package com.technical.assessment;

import com.technical.assessment.components.configurations.MicroserviceConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableAutoConfiguration
@SuppressWarnings("DLS_DEAD_LOCAL_STORE")
@EnableConfigurationProperties({MicroserviceConfiguration.class})
public abstract class MicroserviceApplicationBase implements ApplicationListener<ContextRefreshedEvent> {

    private final MicroserviceConfiguration microserviceConfiguration;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("*******************************************************************");
        log.info("Microservice {} is up and running", microserviceConfiguration.getMicroserviceName());
        log.info("Version: {}", microserviceConfiguration.getMicroserviceVersion());
        log.info("Description: {}", microserviceConfiguration.getMicroserviceDescription());
        log.info("Base path: {}", microserviceConfiguration.getMicroserviceBasePath());
        log.info("*******************************************************************");
    }
}
