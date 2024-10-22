package com.technical.assessment.services.implementations.movements;


import com.technical.assessment.components.enums.ValidationProcessResult;
import com.technical.assessment.components.exceptions.CommonProcessRuntimeException;
import com.technical.assessment.components.helper.TransactionLogErrorHelper;
import com.technical.assessment.components.helper.TransactionValidationHelper;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionService;
import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.dtos.AccountMovementTransactionComposition;
import com.technical.assessment.dtos.TransactionValidationResult;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.services.contracts.IMovementInfrastructureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.*;


@Log4j2
@AllArgsConstructor
@Component(MicroserviceConstants.MOVEMENT_TRANSACTION_ID)
public class AccountMovementTransaction implements
        IIntegrationTransactionService<MovementTransactionRequestDto, MovementResultInformationDto> {

    private final IMovementInfrastructureService service;

    @Override
    public Mono<IntegrationResponse<MovementResultInformationDto>> doOnExecuteTransaction(MovementTransactionRequestDto request) {
        return doOnLoadContextConfiguration(request)
                .flatMap(this::doOnValidateTransactionRequest)
                .flatMap(this::doOnRetrieveTransactionInformation)
                .flatMap(this::doOnExecuteAccountMovement)
                .flatMap(this::doOnValidateTransactionResponse)
                .flatMap(this::doOnProcessTransactionResponse)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_EXECUTE_TRANSACTION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_EXECUTE_TRANSACTION, throwable.getMessage()))
                .log();
    }

    private Mono<AccountMovementTransactionComposition> doOnLoadContextConfiguration(MovementTransactionRequestDto request) {

        final AccountMovementTransactionComposition composition = AccountMovementTransactionComposition.builder()
                .transactionRequest(request)
                .build();
        return Mono.just(composition)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_LOAD_CONTEXT_CONFIGURATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_LOAD_CONTEXT_CONFIGURATION, throwable.getMessage()));
    }

    private Mono<AccountMovementTransactionComposition> doOnValidateTransactionRequest(final AccountMovementTransactionComposition transactionComposition) {
        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final MovementTransactionRequestDto request = transactionComposition.getTransactionRequest();
                    final TransactionValidationResult transactionValidationResult = TransactionValidationHelper.validateMandatoryFields(request);

                    if (ValidationProcessResult.PROCESS_FAILED.equals(transactionValidationResult.getValidationProcessResult())) {
                        TransactionLogErrorHelper.writeErrors(PROCESS_VALIDATION_TRANSACTION_REQUEST, transactionValidationResult.getErrors());
                        return Mono.error(() -> new CommonProcessRuntimeException("error in " + PROCESS_VALIDATION_TRANSACTION_REQUEST + " for AccountMovementTransaction"));
                    }

                    return Mono.just(currentTransactionComposition);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATION_TRANSACTION_REQUEST, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATION_TRANSACTION_REQUEST, throwable.getMessage()));
    }

    private Mono<AccountMovementTransactionComposition> doOnRetrieveTransactionInformation(final AccountMovementTransactionComposition transactionComposition) {
        MovementTransactionRequestDto request = transactionComposition.getTransactionRequest();
        return Mono.just(transactionComposition)
                .flatMap(currentTransactionComposition -> service.doOnRetrieveTransactionCompositionInformation(request)
                        .flatMap(movement -> {
                            currentTransactionComposition.setMovement(movement);
                            return Mono.just(currentTransactionComposition);
                        }))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    private Mono<AccountMovementTransactionComposition> doOnExecuteAccountMovement(final AccountMovementTransactionComposition transactionComposition) {
        return Mono.just(transactionComposition)
                .flatMap(currentTransactionComposition -> service.doOnExecuteAccountMovementTransaction(currentTransactionComposition.getMovement())
                        .flatMap(movementResultInformationDto -> {
                            currentTransactionComposition.setTransactionResponse(movementResultInformationDto);
                            return Mono.just(currentTransactionComposition);
                        }))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    private Mono<AccountMovementTransactionComposition> doOnValidateTransactionResponse(final AccountMovementTransactionComposition transactionComposition) {
        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final MovementResultInformationDto movementResultInformationDto = transactionComposition.getTransactionResponse();
                    final TransactionValidationResult transactionValidationResult = TransactionValidationHelper.validateMandatoryFields(movementResultInformationDto);

                    if (ValidationProcessResult.PROCESS_FAILED.equals(transactionValidationResult.getValidationProcessResult())) {
                        TransactionLogErrorHelper.writeErrors(PROCESS_VALIDATE_TRANSACTION_RESPONSE, transactionValidationResult.getErrors());
                        return Mono.error(() -> new CommonProcessRuntimeException("error in " + PROCESS_VALIDATE_TRANSACTION_RESPONSE + " for AccountMovementTransaction"));
                    }

                    return Mono.just(currentTransactionComposition);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATE_TRANSACTION_RESPONSE, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATE_TRANSACTION_RESPONSE, throwable.getMessage()));
    }

    private Mono<IntegrationResponse<MovementResultInformationDto>> doOnProcessTransactionResponse(final AccountMovementTransactionComposition transactionComposition) {

        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final MovementResultInformationDto movementResultInformationDto = transactionComposition.getTransactionResponse();
                    final IntegrationResponse<MovementResultInformationDto> integrationResponse = IntegrationResponse.<MovementResultInformationDto>builder()
                            .rc(HttpStatus.OK.getReasonPhrase())
                            .msg("Transaction executed successfully")
                            .data(movementResultInformationDto)
                            .build();
                    return Mono.just(integrationResponse);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_PROCESS_TRANSACTION_RESPONSE, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_PROCESS_TRANSACTION_RESPONSE, throwable.getMessage()));
    }
}