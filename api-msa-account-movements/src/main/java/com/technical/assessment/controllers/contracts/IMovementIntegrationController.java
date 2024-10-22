package com.technical.assessment.controllers.contracts;

import com.technical.assessment.constants.MicroserviceConstants;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.MovementListInformationDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER;
import static com.technical.assessment.constants.DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_ACCOUNT_NUMBER;

@Validated
@Api(value = MicroserviceConstants.MICROSERVICE_MOVEMENTS_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = MicroserviceConstants.MICROSERVICE_MOVEMENTS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface IMovementIntegrationController {

    Mono<ResponseEntity<IntegrationResponse<MovementResultInformationDto>>> doOnExecuteMovementTransaction(@RequestBody MovementTransactionRequestDto request);
    Mono<ResponseEntity<IntegrationResponse<MovementListInformationDto>>> doOnExecuteAccountMovements(@RequestParam(MOVEMENT_REQUEST_INTEGRATION_FIELD_ACCOUNT_NUMBER) final String accountNumber);
    Mono<ResponseEntity<IntegrationResponse<MovementListInformationDto>>> doOnExecuteCustomerAccountsMovements(@RequestParam(CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER) final String identificationNumber);

}