package com.bpichincha.assessment.services.implementations;

import com.bpichincha.assessment.constants.LoggerConstants;
import com.bpichincha.assessment.constants.MicroserviceConstants;
import com.bpichincha.assessment.dtos.requests.CustomerInformationRequestDto;
import com.bpichincha.assessment.dtos.responses.CustomerInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import com.bpichincha.assessment.services.contracts.ICustomerIntegrationService;
import com.bpichincha.assessment.components.services.contracts.IIntegrationTransactionFactory;
import com.bpichincha.assessment.components.services.contracts.IIntegrationTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.bpichincha.assessment.constants.ProcessConstants.EXECUTE_CUSTOMER_INFORMATION_TRANSACTION;


@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerIntegrationService implements ICustomerIntegrationService {

    private final IIntegrationTransactionFactory integrationTransactionFactory;

    @Override
    public Mono<IntegrationResponse<CustomerInformationResponseDto>> doOnExecuteCustomerInformationTransaction(final String identificationNumber) {

        return Mono.just(identificationNumber).flatMap(currentTransactionRequest -> {

                final IIntegrationTransactionService<CustomerInformationRequestDto, CustomerInformationResponseDto> integrationTransactionService = integrationTransactionFactory
                    .factory(MicroserviceConstants.BEAN_CUSTOMER_TRANSACTION_ID);

                CustomerInformationRequestDto request = CustomerInformationRequestDto.builder()
                        .identification(identificationNumber)
                        .build();

                return integrationTransactionService.doOnExecuteTransaction(request);
            })
            .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_INFORMATION_TRANSACTION, success.toString()))
            .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_INFORMATION_TRANSACTION, throwable.getMessage()))
            .log();
    }

}