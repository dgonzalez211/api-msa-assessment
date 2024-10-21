package com.bpichincha.assessment.repository.contracts;

import com.bpichincha.assessment.components.exceptions.CustomerNotFoundException;
import com.bpichincha.assessment.components.mappers.ICustomerMapper;
import com.bpichincha.assessment.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.BiFunction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerReactiveRepositoryTest {

    @Mock
    private DatabaseClient databaseClient;

    @Mock
    private ICustomerMapper mapper;

    @Mock
    private DatabaseClient.GenericExecuteSpec genericExecuteSpec;

    @Mock
    private RowsFetchSpec<Customer> rowsFetchSpec;

    private CustomerReactiveRepository customerReactiveRepository;

    @BeforeEach
    void setUp() {
        customerReactiveRepository = new CustomerReactiveRepository(databaseClient, mapper);
    }

    @Test
    void findCustomerByIdentification_Success() {
        String identification = "123456789";
        Customer customer = Customer.builder().identification(identification).build();

        when(databaseClient.sql(anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.bind(anyString(), anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.map(any(BiFunction.class))).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.first()).thenReturn(Mono.just(customer));

        StepVerifier.create(customerReactiveRepository.findCustomerByIdentification(identification))
                .expectNext(customer)
                .verifyComplete();
    }

    @Test
    void findCustomerByIdentification_NotFound() {
        String identification = "123456789";

        when(databaseClient.sql(anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.bind(anyString(), anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.map(any(BiFunction.class))).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.first()).thenReturn(Mono.empty());

        StepVerifier.create(customerReactiveRepository.findCustomerByIdentification(identification))
                .expectError(CustomerNotFoundException.class)
                .verify();
    }

    @Test
    void findAllCustomers_Success() {
        Customer customer1 = Customer.builder().identification("123").build();
        Customer customer2 = Customer.builder().identification("456").build();

        when(databaseClient.sql(anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.map(any(BiFunction.class))).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.all()).thenReturn(Flux.just(customer1, customer2));

        StepVerifier.create(customerReactiveRepository.findAllCustomers())
                .expectNext(customer1, customer2)
                .verifyComplete();
    }

    @Test
    void findAllCustomers_Empty() {
        when(databaseClient.sql(anyString())).thenReturn(genericExecuteSpec);
        when(genericExecuteSpec.map(any(BiFunction.class))).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.all()).thenReturn(Flux.empty());

        StepVerifier.create(customerReactiveRepository.findAllCustomers())
                .expectError(CustomerNotFoundException.class)
                .verify();
    }
}