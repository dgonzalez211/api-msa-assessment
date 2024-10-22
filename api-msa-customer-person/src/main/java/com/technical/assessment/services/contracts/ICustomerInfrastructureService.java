package com.technical.assessment.services.contracts;

import com.technical.assessment.dtos.requests.CustomerInformationRequestDto;
import com.technical.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.entities.Customer;
import reactor.core.publisher.Mono;

public interface ICustomerInfrastructureService {

    Mono<Customer> doOnRetrieveCustomerInformation(CustomerInformationRequestDto request);
    Mono<IntegrationResponse<CustomerListInformationResponseDto>> doOnExecuteAllCustomersInformationTransaction();
}