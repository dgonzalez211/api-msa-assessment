package com.technical.assessment.components.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenAPIConfiguration {

    private static final String GITHUB_URL = "https://github.com/dgonzalez211";
    private static final String MIT_LICENSE = "https://opensource.org/license/mit";

    @Value("${spring.application.version}")
    String appVersion;

    @Value("${spring.application.name}")
    String appName;

    @Value("${spring.webflux.base-path}")
    String contextPath;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(contextPath);
        devServer.setDescription("Server deployment");

        Contact contact = new Contact();
        contact.setEmail("...");
        contact.setName("Diego Gonzalez");
        contact.setUrl(GITHUB_URL);

        License mitLicense = new License()
            .name("MIT License")
            .url(MIT_LICENSE);

        Info info = new Info()
            .title(appName)
            .version(appVersion)
            .contact(contact)
            .description("This code is licensed under the MIT License – see the LICENSE file for details.")
            .termsOfService(MIT_LICENSE)
            .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}
