package com.bpichincha.assessment.services.contracts;

import com.bpichincha.assessment.dtos.requests.MovementTransactionRequestDto;
import com.bpichincha.assessment.dtos.responses.MovementResultInformationDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import reactor.core.publisher.Mono;

public interface IMovementIntegrationService {

    Mono<IntegrationResponse<MovementResultInformationDto>> doOnExecuteMovementTransaction(final MovementTransactionRequestDto request);
}