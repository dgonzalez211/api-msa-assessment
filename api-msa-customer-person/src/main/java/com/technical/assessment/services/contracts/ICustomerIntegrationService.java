package com.technical.assessment.services.contracts;

import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import reactor.core.publisher.Mono;

public interface ICustomerIntegrationService {

    Mono<IntegrationResponse<CustomerInformationResponseDto>> doOnExecuteCustomerInformationTransaction(final String identificationNumber);

}