package com.technical.assessment.services.implementations;

import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.dtos.requests.CustomerInformationRequestDto;
import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.services.contracts.ICustomerIntegrationService;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionFactory;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.EXECUTE_CUSTOMER_INFORMATION_TRANSACTION;


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