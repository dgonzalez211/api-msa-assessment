package com.technical.assessment.components.configurations;

import com.technical.assessment.constants.ConfigurationConstants;
import lombok.Getter;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "spring")
public class MicroserviceConfiguration {

    @Value(ConfigurationConstants.MICROSERVICE_CONFIGURATION_UUID)
    private String entityUuid;

    @Value("${spring.application.name}")
    private String microserviceName;

    @Value("${spring.application.version}")
    private String microserviceVersion;

    @Value("${spring.application.description}")
    private String microserviceDescription;

    @Value("${spring.webflux.base-path}")
    private String microserviceBasePath;

    @Value("${spring.flyway.url}")
    private String flywayUrl;

    @Value("${spring.flyway.user}")
    private String flywayUser;

    @Value("${spring.flyway.password}")
    private String flywayPassword;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(flywayUrl, flywayUser, flywayPassword)
                .baselineOnMigrate(true)
                .load();
    }
}