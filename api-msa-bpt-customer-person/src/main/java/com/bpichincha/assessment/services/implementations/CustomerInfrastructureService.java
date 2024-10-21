package com.bpichincha.assessment.services.implementations;

import com.bpichincha.assessment.constants.LoggerConstants;
import com.bpichincha.assessment.constants.ResponseCodes;
import com.bpichincha.assessment.dtos.requests.CustomerInformationRequestDto;
import com.bpichincha.assessment.dtos.responses.CustomerInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.CustomerListInformationResponseDto;
import com.bpichincha.assessment.dtos.responses.IntegrationResponse;
import com.bpichincha.assessment.entities.Customer;
import com.bpichincha.assessment.repository.contracts.CustomerReactiveRepository;
import com.bpichincha.assessment.services.contracts.ICustomerInfrastructureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.bpichincha.assessment.constants.MicroserviceConstants.SUCCESS_MESSAGE_CUSTOMER_FOUND;
import static com.bpichincha.assessment.constants.ProcessConstants.PROCESS_RETRIEVE_TRANSACTION_INFORMATION;
import static com.bpichincha.assessment.constants.ResponseCodes.OK;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerInfrastructureService implements ICustomerInfrastructureService {

    private final CustomerReactiveRepository repository;

    @Override
    public Mono<Customer> doOnRetrieveCustomerInformation(CustomerInformationRequestDto request) {
        return repository.findCustomerByIdentification(request.getIdentification())
                .doOnNext(customer -> log.info(SUCCESS_MESSAGE_CUSTOMER_FOUND, customer))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    @Override
    public Mono<IntegrationResponse<CustomerListInformationResponseDto>> doOnExecuteAllCustomersInformationTransaction() {
        return repository.findAllCustomers()
                .map(customer -> CustomerInformationResponseDto.builder()
                        .identification(customer.getIdentification())
                        .age(customer.getAge())
                        .gender(customer.getGender())
                        .phone(customer.getPhone())
                        .address(customer.getAddress())
                        .membershipNumber(customer.getMembershipNumber())
                        .name(customer.getName())
                        .build())
                .collectList()
                .map(customers -> IntegrationResponse.<CustomerListInformationResponseDto>builder()
                        .rc(OK)
                        .msg("Customers retrieved successfully")
                        .data(CustomerListInformationResponseDto.builder()
                                .customers(customers)
                                .build())
                        .build())
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }
}
