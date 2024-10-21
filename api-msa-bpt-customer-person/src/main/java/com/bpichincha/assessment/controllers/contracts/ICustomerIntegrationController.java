package com.bpichincha.assessment.controllers.contracts;

import com.bpichincha.assessment.constants.MicroserviceConstants;
import com.bpichincha.assessment.dtos.responses.CustomerInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Validated
@Api(value = MicroserviceConstants.MICROSERVICE_CUSTOMERS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = MicroserviceConstants.MICROSERVICE_CUSTOMERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface ICustomerIntegrationController {

    Mono<ResponseEntity<IntegrationResponse<CustomerInformationResponseDto>>> doOnExecuteCustomerInformationTransaction(String identificationNumber);
    Mono<ResponseEntity<IntegrationResponse<CustomerListInformationResponseDto>>> doOnExecuteCustomersInformation();
}