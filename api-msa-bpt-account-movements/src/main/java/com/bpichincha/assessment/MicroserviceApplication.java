package com.bpichincha.assessment;

import com.bpichincha.assessment.components.configurations.MicroserviceConfiguration;
import com.bpichincha.assessment.constants.LoggerConstants;
import io.micrometer.context.ContextRegistry;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

import javax.validation.constraints.NotNull;

@SpringBootApplication(
    scanBasePackages = {
        "com.bpichincha.assessment",
        "com.bpichincha.assessment.entities",
        "com.bpichincha.assessment.components.configurations",
        "com.bpichincha.assessment.components.handlers",
        "com.bpichincha.assessment.components.interceptors",
        "com.bpichincha.assessment.services.implementations",
        "com.bpichincha.assessment.repository.contracts",
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
