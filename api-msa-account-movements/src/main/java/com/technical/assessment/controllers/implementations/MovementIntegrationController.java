package com.technical.assessment.controllers.implementations;

import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.controllers.contracts.IMovementIntegrationController;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.MovementListInformationDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.services.contracts.IMovementInfrastructureService;
import com.technical.assessment.services.contracts.IMovementIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MovementIntegrationController implements IMovementIntegrationController {

    private final IMovementIntegrationService integrationService;
    private final IMovementInfrastructureService infrastructureService;

    @Override
    @PostMapping(path = MicroserviceConstants.MICROSERVICE_MOVEMENTS_TRANSACTION_EXCHANGE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IntegrationResponse<MovementResultInformationDto>>> doOnExecuteMovementTransaction(@RequestBody MovementTransactionRequestDto request) {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD);
        return integrationService.doOnExecuteMovementTransaction(request)
            .flatMap(currentIntegrationResponse -> Mono.just(new ResponseEntity<>(currentIntegrationResponse, HttpStatus.OK)))
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD, success.toString()))
            .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD, throwable.getMessage()))
            .log();
    }

    @Override
    @GetMapping(path = MicroserviceConstants.MICROSERVICE_ACCOUNTS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IntegrationResponse<MovementListInformationDto>>> doOnExecuteAccountMovements(@RequestParam("accountNumber") final String accountNumber) {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, EXECUTE_ACCOUNT_MOVEMENTS_LOG_METHOD);
        return infrastructureService.doOnRetrieveTransactionListInformation(accountNumber)
                .flatMap(currentIntegrationResponse -> Mono.just(new ResponseEntity<>(currentIntegrationResponse, HttpStatus.OK)))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_ACCOUNT_MOVEMENTS_LOG_METHOD, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_ACCOUNT_MOVEMENTS_LOG_METHOD, throwable.getMessage()))
                .log();
    }

    @Override
    @GetMapping(path = MicroserviceConstants.MICROSERVICE_REPORTS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IntegrationResponse<MovementListInformationDto>>> doOnExecuteCustomerAccountsMovements(@RequestParam("identificationNumber") final String identificationNumber) {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, EXECUTE_CUSTOMER_ACCOUNTS_MOVEMENTS_LOG_METHOD);
        return infrastructureService.doOnRetrieveCustomerMovementsListInformation(identificationNumber)
                .flatMap(currentIntegrationResponse -> Mono.just(new ResponseEntity<>(currentIntegrationResponse, HttpStatus.OK)))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_ACCOUNTS_MOVEMENTS_LOG_METHOD, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_ACCOUNTS_MOVEMENTS_LOG_METHOD, throwable.getMessage()))
                .log();
    }

}