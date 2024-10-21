package com.bpichincha.assessment.services.implementations.customers;


import com.bpichincha.assessment.components.enums.ValidationProcessResult;
import com.bpichincha.assessment.components.exceptions.CommonProcessRuntimeException;
import com.bpichincha.assessment.components.helper.TransactionLogErrorHelper;
import com.bpichincha.assessment.components.helper.TransactionValidationHelper;
import com.bpichincha.assessment.components.mappers.ICustomerMapper;
import com.bpichincha.assessment.components.services.contracts.IIntegrationTransactionService;
import com.bpichincha.assessment.constants.LoggerConstants;
import com.bpichincha.assessment.constants.MicroserviceConstants;
import com.bpichincha.assessment.dtos.CustomerInformationComposition;
import com.bpichincha.assessment.dtos.TransactionValidationResult;
import com.bpichincha.assessment.dtos.requests.CustomerInformationRequestDto;
import com.bpichincha.assessment.dtos.responses.CustomerInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import com.bpichincha.assessment.services.contracts.ICustomerInfrastructureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.bpichincha.assessment.constants.ProcessConstants.*;


@Log4j2
@AllArgsConstructor
@Component(MicroserviceConstants.BEAN_CUSTOMER_TRANSACTION_ID)
public class CustomerInformationTransaction implements
        IIntegrationTransactionService<CustomerInformationRequestDto, CustomerInformationResponseDto> {

    private final ICustomerInfrastructureService service;
    private final ICustomerMapper mapper;

    @Override
    public Mono<IntegrationResponse<CustomerInformationResponseDto>> doOnExecuteTransaction(CustomerInformationRequestDto request) {
        return doOnLoadContextConfiguration(request)
                .flatMap(this::doOnValidateTransactionRequest)
                .flatMap(this::doOnRetrieveTransactionInformation)
                .flatMap(this::doOnValidateTransactionResponse)
                .flatMap(this::doOnProcessTransactionResponse)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_EXECUTE_TRANSACTION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_EXECUTE_TRANSACTION, throwable.getMessage()))
                .log();
    }

    private Mono<CustomerInformationComposition> doOnLoadContextConfiguration(CustomerInformationRequestDto request) {
        final CustomerInformationComposition composition = CustomerInformationComposition.builder()
                .transactionRequest(request)
                .build();
        return Mono.just(composition)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_LOAD_CONTEXT_CONFIGURATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_LOAD_CONTEXT_CONFIGURATION, throwable.getMessage()));
    }

    private Mono<CustomerInformationComposition> doOnValidateTransactionRequest(final CustomerInformationComposition transactionComposition) {
        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final CustomerInformationRequestDto request = transactionComposition.getTransactionRequest();
                    final TransactionValidationResult transactionValidationResult = TransactionValidationHelper.validateMandatoryFields(request);

                    if (ValidationProcessResult.PROCESS_FAILED.equals(transactionValidationResult.getValidationProcessResult())) {
                        TransactionLogErrorHelper.writeErrors(PROCESS_VALIDATION_TRANSACTION_REQUEST, transactionValidationResult.getErrors());
                        return Mono.error(() -> new CommonProcessRuntimeException("error in " + PROCESS_VALIDATION_TRANSACTION_REQUEST + " for CustomerBalanceTransaction"));
                    }

                    return Mono.just(currentTransactionComposition);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATION_TRANSACTION_REQUEST, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATION_TRANSACTION_REQUEST, throwable.getMessage()));
    }

    private Mono<CustomerInformationComposition> doOnRetrieveTransactionInformation(final CustomerInformationComposition transactionComposition) {
        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final CustomerInformationRequestDto request = transactionComposition.getTransactionRequest();

                    return service.doOnRetrieveCustomerInformation(request)
                            .flatMap(customerEntity -> {
                                currentTransactionComposition.setCustomerEntity(customerEntity);

                                final CustomerInformationResponseDto customerInformationResponseDto = mapper.mapperToCustomerInformation(customerEntity);

                                currentTransactionComposition.setTransactionResponse(customerInformationResponseDto);
                                return Mono.just(currentTransactionComposition);
                            });
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    private Mono<CustomerInformationComposition> doOnValidateTransactionResponse(final CustomerInformationComposition transactionComposition) {
        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final CustomerInformationResponseDto customerInformationResponseDto = transactionComposition.getTransactionResponse();
                    final TransactionValidationResult transactionValidationResult = TransactionValidationHelper.validateMandatoryFields(customerInformationResponseDto);

                    if (ValidationProcessResult.PROCESS_FAILED.equals(transactionValidationResult.getValidationProcessResult())) {
                        TransactionLogErrorHelper.writeErrors(PROCESS_VALIDATE_TRANSACTION_RESPONSE, transactionValidationResult.getErrors());
                        return Mono.error(() -> new CommonProcessRuntimeException("error in " + PROCESS_VALIDATE_TRANSACTION_RESPONSE + " for CustomerBalanceTransaction"));
                    }

                    return Mono.just(currentTransactionComposition);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATE_TRANSACTION_RESPONSE, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_VALIDATE_TRANSACTION_RESPONSE, throwable.getMessage()));
    }

    private Mono<IntegrationResponse<CustomerInformationResponseDto>> doOnProcessTransactionResponse(final CustomerInformationComposition transactionComposition) {

        return Mono.just(transactionComposition).flatMap(currentTransactionComposition -> {

                    final CustomerInformationResponseDto customerInformationResponseDto = transactionComposition.getTransactionResponse();
                    final IntegrationResponse<CustomerInformationResponseDto> integrationResponse = IntegrationResponse.<CustomerInformationResponseDto>builder()
                            .rc(HttpStatus.OK.getReasonPhrase())
                            .msg("Customer Information retrieved successfully")
                            .data(customerInformationResponseDto)
                            .build();
                    return Mono.just(integrationResponse);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_PROCESS_TRANSACTION_RESPONSE, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_PROCESS_TRANSACTION_RESPONSE, throwable.getMessage()));
    }
}