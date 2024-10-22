package com.technical.assessment.services.contracts;

import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.dtos.responses.MovementListInformationDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.entities.Movement;
import reactor.core.publisher.Mono;

public interface IMovementInfrastructureService {

    Mono<MovementResultInformationDto> doOnExecuteAccountMovementTransaction(Movement movement);
    Mono<Movement> doOnRetrieveTransactionCompositionInformation(MovementTransactionRequestDto request);
    Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveTransactionListInformation(String accountNumber);
    Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveCustomerMovementsListInformation(String customerIdentification);
}