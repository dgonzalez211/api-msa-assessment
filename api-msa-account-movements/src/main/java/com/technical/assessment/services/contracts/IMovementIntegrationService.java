package com.technical.assessment.services.contracts;

import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import reactor.core.publisher.Mono;

public interface IMovementIntegrationService {

    Mono<IntegrationResponse<MovementResultInformationDto>> doOnExecuteMovementTransaction(final MovementTransactionRequestDto request);
}