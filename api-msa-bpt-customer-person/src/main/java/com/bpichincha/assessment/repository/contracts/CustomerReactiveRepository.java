package com.bpichincha.assessment.repository.contracts;

import com.bpichincha.assessment.components.exceptions.CustomerNotFoundException;
import com.bpichincha.assessment.components.mappers.ICustomerMapper;
import com.bpichincha.assessment.constants.LoggerConstants;
import com.bpichincha.assessment.constants.SqlQueryConstants;
import com.bpichincha.assessment.entities.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bpichincha.assessment.constants.DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION;
import static com.bpichincha.assessment.constants.MicroserviceConstants.ERROR_MESSAGE_CUSTOMERS_NOT_FOUND;
import static com.bpichincha.assessment.constants.MicroserviceConstants.ERROR_MESSAGE_CUSTOMER_NOT_FOUND;
import static com.bpichincha.assessment.constants.ProcessConstants.EXECUTE_FIND_ALL_CUSTOMERS;
import static com.bpichincha.assessment.constants.ProcessConstants.EXECUTE_FIND_CUSTOMER_BY_IDENTIFICATION;
import static com.bpichincha.assessment.constants.SqlQueryConstants.SQL_QUERY_FIND_ALL_CUSTOMERS;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerReactiveRepository {

    private final DatabaseClient databaseClient;
    private final ICustomerMapper mapper;

    public Mono<Customer> findCustomerByIdentification(String identification) {
        return databaseClient.sql(SqlQueryConstants.SQL_QUERY_FIND_CUSTOMER_BY_IDENTIFICATION)
                .bind(CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION, identification)
                .map((row, rowMetadata) -> mapper.mapToCustomer(row))
                .first()
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(ERROR_MESSAGE_CUSTOMER_NOT_FOUND, identification)))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_FIND_CUSTOMER_BY_IDENTIFICATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_FIND_CUSTOMER_BY_IDENTIFICATION, throwable.getMessage()));
    }

    public Flux<Customer> findAllCustomers() {
        return databaseClient.sql(SQL_QUERY_FIND_ALL_CUSTOMERS)
                .map((row, rowMetadata) -> mapper.mapToCustomer(row))
                .all()
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(ERROR_MESSAGE_CUSTOMERS_NOT_FOUND)))
                .doOnComplete(() -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_FIND_ALL_CUSTOMERS, "All customers retrieved"))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_FIND_ALL_CUSTOMERS, throwable.getMessage()));
    }
}