package com.bpichincha.assessment.components.helper;

import lombok.experimental.UtilityClass;
import org.springframework.web.server.ServerWebExchange;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@UtilityClass
public class CommonInterceptorHelper {

    public static final Function<ServerWebExchange, Boolean> validateActuator =
            (serverWebExchange -> !Objects.isNull(serverWebExchange) && serverWebExchange.getRequest().getURI().getPath().contains("/actuator"));

    public static final Function<ServerWebExchange, Boolean> validateCommonHealthEndpoints =
            (serverWebExchange -> !Objects.isNull(serverWebExchange) && serverWebExchange.getRequest().getURI().getPath().contains("/health"));

    public static String generateRequestId() {
        return String.format("%s-%s", "bpa-req", UUID.randomUUID()).toUpperCase(Locale.getDefault());
    }
}
