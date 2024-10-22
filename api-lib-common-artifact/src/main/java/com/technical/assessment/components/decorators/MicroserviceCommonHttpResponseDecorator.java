package com.technical.assessment.components.decorators;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MicroserviceCommonHttpResponseDecorator extends ServerHttpResponseDecorator {
    private final DataBufferFactory bufferFactory = super.bufferFactory();

    public MicroserviceCommonHttpResponseDecorator(ServerHttpResponse serverHttpResponse) {
        super(serverHttpResponse);
    }

    @NonNull
    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        super.getHeaders().add("X-Request-ID", ThreadContext.get("requestId"));
        log.info("Response type [{}] from URL [{}] with status code [{}]", body, super.getHeaders().getFirst("Location"), super.getStatusCode());
        if (body instanceof Flux || body instanceof Mono) {
            return super.writeWith(Flux.from(body).map(dataBuffer -> {
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);
                String contentRaw = new String(content, StandardCharsets.UTF_8);
                log.info("Headers [{}]", super.getHeaders());
                log.info("Response body [{}]", contentRaw);
                return bufferFactory.wrap(contentRaw.getBytes(StandardCharsets.UTF_8));
            }));
        }
        return super.writeWith(body);
    }
}
