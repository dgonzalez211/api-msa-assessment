package com.bpichincha.assessment.services.contracts;

import com.bpichincha.assessment.dtos.requests.CustomerInformationRequestDto;
import com.bpichincha.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import com.bpichincha.assessment.entities.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerInfrastructureService {

    Mono<Customer> doOnRetrieveCustomerInformation(CustomerInformationRequestDto request);
    Mono<IntegrationResponse<CustomerListInformationResponseDto>> doOnExecuteAllCustomersInformationTransaction();
}