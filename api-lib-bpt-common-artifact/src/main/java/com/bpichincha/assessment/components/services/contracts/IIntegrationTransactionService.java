package com.bpichincha.assessment.components.services.contracts;

import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import reactor.core.publisher.Mono;

import java.io.Serializable;


public interface IIntegrationTransactionService<T extends Serializable, R extends Serializable> {

    Mono<IntegrationResponse<R>> doOnExecuteTransaction(T transactionRequest);
}