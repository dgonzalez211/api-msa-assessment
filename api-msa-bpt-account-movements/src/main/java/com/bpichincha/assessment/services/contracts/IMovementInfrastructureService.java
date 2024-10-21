package com.bpichincha.assessment.services.contracts;

import com.bpichincha.assessment.dtos.requests.MovementTransactionRequestDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import com.bpichincha.assessment.dtos.responses.MovementListInformationDto;
import com.bpichincha.assessment.dtos.responses.MovementResultInformationDto;
import com.bpichincha.assessment.entities.Movement;
import reactor.core.publisher.Mono;

public interface IMovementInfrastructureService {

    Mono<MovementResultInformationDto> doOnExecuteAccountMovementTransaction(Movement movement);
    Mono<Movement> doOnRetrieveTransactionCompositionInformation(MovementTransactionRequestDto request);
    Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveTransactionListInformation(String accountNumber);
    Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveCustomerMovementsListInformation(String customerIdentification);
}