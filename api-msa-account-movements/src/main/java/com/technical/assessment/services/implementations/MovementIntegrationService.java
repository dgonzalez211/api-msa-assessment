package com.technical.assessment.services.implementations;

import com.technical.assessment.components.services.contracts.IIntegrationTransactionFactory;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionService;
import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.services.contracts.IMovementIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovementIntegrationService implements IMovementIntegrationService {

    private final IIntegrationTransactionFactory integrationTransactionFactory;
    

    @Override
    public Mono<IntegrationResponse<MovementResultInformationDto>> doOnExecuteMovementTransaction(final MovementTransactionRequestDto request) {

        return Mono.just(request).flatMap(currentTransactionRequest -> {

                final IIntegrationTransactionService<MovementTransactionRequestDto, MovementResultInformationDto> integrationTransactionService = integrationTransactionFactory
                    .factory(MicroserviceConstants.MOVEMENT_TRANSACTION_ID);

                return integrationTransactionService.doOnExecuteTransaction(request);
            })
            .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD, success.toString()))
            .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD, throwable.getMessage()))
            .log();
    }

}