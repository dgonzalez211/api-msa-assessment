package com.bpichincha.assessment.services.contracts;

import com.bpichincha.assessment.dtos.responses.CustomerInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import reactor.core.publisher.Mono;

public interface ICustomerIntegrationService {

    Mono<IntegrationResponse<CustomerInformationResponseDto>> doOnExecuteCustomerInformationTransaction(final String identificationNumber);

}