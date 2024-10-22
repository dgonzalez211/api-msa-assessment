package com.technical.assessment.controllers.implementations;

import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.services.contracts.ICustomerInfrastructureService;
import com.technical.assessment.services.contracts.ICustomerIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerIntegrationController.class)
class CustomerIntegrationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICustomerIntegrationService customerIntegrationService;

    @MockBean
    private ICustomerInfrastructureService customerInfrastructureService;

    @Test
    void doOnExecuteCustomerInformationTransaction_Success() {
        String identificationNumber = "123456789";
        CustomerInformationResponseDto responseDto = CustomerInformationResponseDto.builder()
                .identification(identificationNumber)
                .name("Diego Gonzalez")
                .build();
        IntegrationResponse<CustomerInformationResponseDto> integrationResponse = IntegrationResponse.<CustomerInformationResponseDto>builder()
                .data(responseDto)
                .httpStatus(HttpStatus.OK)
                .build();

        when(customerIntegrationService.doOnExecuteCustomerInformationTransaction(anyString()))
                .thenReturn(Mono.just(integrationResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customers/information")
                        .queryParam("identificationNumber", identificationNumber)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.identificationNumber").isEqualTo(identificationNumber)
                .jsonPath("$.data.name").isEqualTo("Diego Gonzalez");
    }

    @Test
    void doOnExecuteCustomersInformation_Success() {
        CustomerInformationResponseDto customer1 = CustomerInformationResponseDto.builder()
                .identification("123")
                .name("Diego Gonzalez")
                .build();
        CustomerInformationResponseDto customer2 = CustomerInformationResponseDto.builder()
                .identification("456")
                .name("Andres Marrugo")
                .build();
        CustomerListInformationResponseDto listResponseDto = CustomerListInformationResponseDto.builder()
                .customers(Arrays.asList(customer1, customer2))
                .build();
        IntegrationResponse<CustomerListInformationResponseDto> integrationResponse = IntegrationResponse.<CustomerListInformationResponseDto>builder()
                .data(listResponseDto)
                .httpStatus(HttpStatus.OK)
                .build();

        when(customerInfrastructureService.doOnExecuteAllCustomersInformationTransaction())
                .thenReturn(Mono.just(integrationResponse));

        webTestClient.get()
                .uri("/customers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.customers[0].identificationNumber").isEqualTo("123")
                .jsonPath("$.data.customers[0].name").isEqualTo("Diego Gonzalez")
                .jsonPath("$.data.customers[1].identificationNumber").isEqualTo("456")
                .jsonPath("$.data.customers[1].name").isEqualTo("Andres Marrugo");
    }
}