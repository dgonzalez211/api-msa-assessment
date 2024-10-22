package com.technical.assessment;

import com.technical.assessment.components.configurations.MicroserviceConfiguration;
import com.technical.assessment.constants.LoggerConstants;
import io.micrometer.context.ContextRegistry;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

import javax.validation.constraints.NotNull;

@SpringBootApplication(
        scanBasePackages = {
                "com.technical.assessment",
                "com.technical.assessment.entities",
                "com.technical.assessment.components.configurations",
                "com.technical.assessment.components.handlers",
                "com.technical.assessment.components.interceptors",
                "com.technical.assessment.services.implementations",
                "com.technical.assessment.repository.contracts",
        }
)
public class MicroserviceApplication extends MicroserviceApplicationBase {

    public MicroserviceApplication(@NotNull MicroserviceConfiguration microserviceConfiguration) {
        super(microserviceConfiguration);
    }

    public static void main(String[] args) {
        enableAutomaticContextPropagation();
        SpringApplication.run(MicroserviceApplication.class, args);
    }

    private static void enableAutomaticContextPropagation() {
        Hooks.enableAutomaticContextPropagation();
        registerThreadLocalAccessor(LoggerConstants.KEY_REQUEST_ID);
    }

    private static void registerThreadLocalAccessor(final String key) {
        ContextRegistry.getInstance().registerThreadLocalAccessor(
                key, () -> ThreadContext.get(key),
                value -> ThreadContext.put(key, value),
                () -> ThreadContext.remove(key)
        );
    }

}
