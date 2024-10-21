package com.bpichincha.assessment.components.decorators;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

@Slf4j
public class MicroserviceCommonHttpRequestDecorator extends ServerHttpRequestDecorator {

    public MicroserviceCommonHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @NonNull
    @Override
    public Flux<DataBuffer> getBody() {
        log.info("Request from: [{}]", super.getPath());
        return super.getBody();
    }

}
