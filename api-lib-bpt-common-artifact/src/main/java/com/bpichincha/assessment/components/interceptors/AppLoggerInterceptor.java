package com.bpichincha.assessment.components.interceptors;

import com.bpichincha.assessment.components.decorators.MicroserviceCommonHttpRequestDecorator;
import com.bpichincha.assessment.components.decorators.MicroserviceCommonHttpResponseDecorator;
import com.bpichincha.assessment.components.helper.CommonInterceptorHelper;
import com.bpichincha.assessment.constants.LoggerConstants;
import com.bpichincha.assessment.constants.WebRequestConstants;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@AllArgsConstructor
public class AppLoggerInterceptor implements WebFilter {

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange serverWebExchange, @NonNull WebFilterChain webFilterChain) {

        if (Boolean.TRUE.equals(CommonInterceptorHelper.validateCommonHealthEndpoints.apply(serverWebExchange))) {
            return webFilterChain.filter(serverWebExchange.mutate().build());
        }

        final var requestId = StringUtils.defaultIfEmpty(serverWebExchange.getRequest().getHeaders().getFirst(WebRequestConstants.KEY_REQUEST_ID), CommonInterceptorHelper.generateRequestId());

        ThreadContext.put(LoggerConstants.KEY_REQUEST_ID, requestId);

        log.trace("requestId [{}]", requestId);

        return webFilterChain
                .filter(
                        serverWebExchange.mutate()
                                .request(new MicroserviceCommonHttpRequestDecorator(serverWebExchange.getRequest()))
                                .response(new MicroserviceCommonHttpResponseDecorator(serverWebExchange.getResponse()))
                                .build()
                )
                .contextWrite(context -> context.put(WebRequestConstants.CONTEXT_KEY, serverWebExchange.getRequest()))
                .contextWrite(context -> context.put(WebRequestConstants.KEY_REQUEST_ID, requestId));
    }
}