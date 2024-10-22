package com.technical.assessment.controllers.implementations;

import com.technical.assessment.constants.DataInformationConstants;
import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.controllers.contracts.ICustomerIntegrationController;
import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.services.contracts.ICustomerInfrastructureService;
import com.technical.assessment.services.contracts.ICustomerIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.EXECUTE_CUSTOMERS_INFORMATION;
import static com.technical.assessment.constants.ProcessConstants.EXECUTE_CUSTOMER_INFORMATION_TRANSACTION;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CustomerIntegrationController implements ICustomerIntegrationController {

    private final ICustomerIntegrationService integrationService;
    private final ICustomerInfrastructureService infrastructureService;

    @Override
    @GetMapping(path = MicroserviceConstants.MICROSERVICE_GET_CUSTOMER_INFORMATION_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IntegrationResponse<CustomerInformationResponseDto>>> doOnExecuteCustomerInformationTransaction(
            @RequestParam(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER) final String identificationNumber) {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, EXECUTE_CUSTOMER_INFORMATION_TRANSACTION);
        return integrationService.doOnExecuteCustomerInformationTransaction(identificationNumber)
            .flatMap(currentIntegrationResponse -> Mono.just(new ResponseEntity<>(currentIntegrationResponse, HttpStatus.OK)))
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_INFORMATION_TRANSACTION, success.toString()))
            .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_INFORMATION_TRANSACTION, throwable.getMessage()))
            .log();
    }

    @Override
    @GetMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IntegrationResponse<CustomerListInformationResponseDto>>> doOnExecuteCustomersInformation() {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, EXECUTE_CUSTOMERS_INFORMATION);
        return infrastructureService.doOnExecuteAllCustomersInformationTransaction()
                .flatMap(currentIntegrationResponse -> Mono.just(new ResponseEntity<>(currentIntegrationResponse, HttpStatus.OK)))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMERS_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMERS_INFORMATION, throwable.getMessage()))
                .log();
    }

}