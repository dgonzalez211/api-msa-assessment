package com.technical.assessment.repository.contracts;

import com.technical.assessment.components.enums.MovementStatus;
import com.technical.assessment.components.enums.MovementType;
import com.technical.assessment.components.exceptions.AccountNotFoundException;
import com.technical.assessment.components.exceptions.CustomerNotFoundException;
import com.technical.assessment.components.exceptions.MovementsNotFoundException;
import com.technical.assessment.components.exceptions.TransactionInformationNotFoundException;
import com.technical.assessment.components.mappers.IAccountMapper;
import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.entities.Account;
import com.technical.assessment.entities.Customer;
import com.technical.assessment.entities.Movement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

import static com.technical.assessment.constants.DataInformationConstants.*;
import static com.technical.assessment.constants.MicroserviceConstants.*;
import static com.technical.assessment.constants.ProcessConstants.*;
import static com.technical.assessment.constants.ResponseCodes.ACCOUNT_NOT_FOUND;
import static com.technical.assessment.constants.SqlQueryConstants.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountReactiveRepository {

    private final DatabaseClient databaseClient;
    private final IAccountMapper mapper;

    public Mono<Movement> doOnExecuteAccountMovement(Movement movement) {
        return Mono.just(movement)
                .flatMap(this::doOnRegisterMovement)
                .flatMap(this::doOnUpdateAccountBalance);

    }

    public Mono<Movement> doOnRetrieveTransactionCompositionInformation(MovementTransactionRequestDto request) {
        return databaseClient
                .sql(SQL_QUERY_GET_ACCOUNT_CUSTOMER)
                .bind(MOVEMENT_REQUEST_INTEGRATION_FIELD_ACCOUNT_NUMBER, request.getOriginAccountNumber())
                .map((row, rowMetadata) -> {
                    Account account = mapper.mapToAccount(row);

                    return Movement.builder()
                            .originAccount(account)
                            .movementType(request.getAmount().compareTo(BigDecimal.ZERO) > 0 ?
                                    MovementType.DEPOSIT.getValue() : MovementType.WITHDRAWAL.getValue())
                            .amount(request.getAmount())
                            .initialBalance(account.getCurrentBalance())
                            .transactionDate(new Date())
                            .movementStatus(MovementStatus.APPROVED.getName())
                            .message(MovementStatus.APPROVED.getName())
                            .build();
                })
                .first()
                .cast(Movement.class)
                .switchIfEmpty(Mono.error(new TransactionInformationNotFoundException(ERROR_MESSAGE_REQUEST_INFORMATION_NOT_FOUND, request.getOriginAccountNumber())))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    public Mono<Account> doOnRetrieveAccountInformation(String accountNumber) {
        return databaseClient
                .sql(SQL_QUERY_GET_ACCOUNT_BY_ACCOUNT_NUMBER)
                .bind(MOVEMENT_REQUEST_INTEGRATION_FIELD_ACCOUNT_NUMBER, accountNumber)
                .map((row, rowMetadata) -> {
                    Account account = mapper.mapToAccount(row);
                    account.setCustomer(mapper.mapToCustomer(row));
                    return account;
                })
                .first()
                .cast(Account.class)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(ACCOUNT_NOT_FOUND, accountNumber)))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    public Mono<Customer> doOnRetrieveCustomerInformation(String identification) {
        return databaseClient.sql(SQL_QUERY_GET_CUSTOMER_BY_IDENTIFICATION)
                .bind(CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION, identification)
                .map((row, rowMetadata) -> mapper.mapToCustomer(row))
                .first()
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(ERROR_MESSAGE_CUSTOMER_NOT_FOUND, identification)))
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    public Mono<Movement> doOnUpdateAccountBalance(Movement movement) {
        return Mono.just(movement)
                .flatMap(currentMovement -> databaseClient
                        .sql(SQL_QUERY_UPDATE_ACCOUNT_BALANCE)
                        .bind(MOVEMENT_REQUEST_INTEGRATION_FIELD_BALANCE, movement.getOriginAccount().getCurrentBalance())
                        .bind(CUSTOMER_REQUEST_INTEGRATION_FIELD_ID, currentMovement.getOriginAccount().getId())
                        .fetch()
                        .rowsUpdated()
                        .thenReturn(currentMovement));
    }

    public Mono<Movement> doOnRegisterMovement(Movement movement) {
        return Mono.just(movement)
                .flatMap(currentMovement -> databaseClient
                        .sql(SQL_QUERY_INSERT_MOVEMENT)
                        .bind("transaction_date", currentMovement.getTransactionDate())
                        .bind("origin_account_id", currentMovement.getOriginAccount().getId())
                        .bind("initial_balance", currentMovement.getInitialBalance())
                        .bind("amount", currentMovement.getAmount())
                        .bind("movement_type", currentMovement.getMovementType())
                        .bind("movement_status", MovementStatus.APPROVED.getCode())
                        .bind("message", MovementStatus.APPROVED.getName())
                        .fetch()
                        .rowsUpdated()
                        .thenReturn(currentMovement));
    }

    public Flux<Movement> doOnRetrieveTransactionListInformation(Account account) {
        return databaseClient.sql(SQL_QUERY_GET_ACCOUNT_MOVEMENTS)
                .bind(MOVEMENT_REQUEST_INTEGRATION_FIELD_ORIGIN_ACCOUNT_ID, account.getId())
                .map((row, rowMetadata) -> {
                    Movement movement = mapper.mapToMovement(row);
                    movement.setOriginAccount(account);
                    return movement;
                })
                .all()
                .switchIfEmpty(Mono.error(new MovementsNotFoundException(ERROR_MESSAGE_ACCOUNT_MOVEMENTS_NOT_FOUND, account.getAccountNumber())))
                .doOnComplete(() -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_ACCOUNT_MOVEMENTS_LIST_LOG_METHOD, SUCCESS_MESSAGE_ACCOUNT_MOVEMENTS_RETRIEVED))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_ACCOUNT_MOVEMENTS_LIST_LOG_METHOD, throwable.getMessage()));
    }

    public Flux<Movement> doOnRetrieveCustomerMovementsListInformation(Customer customer) {
        return databaseClient.sql(SQL_QUERY_GET_CUSTOMER_MOVEMENTS)
                .bind("customerId", customer.getId())
                .map((row, rowMetadata) -> {
                    Movement movement = mapper.mapToMovement(row);
                    movement.setOriginAccount(mapper.mapToAccount(row));
                    return movement;
                })
                .all()
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(ERROR_MESSAGE_NO_CUSTOMERS_FOUND, customer.getIdentification())))
                .doOnComplete(() -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_MOVEMENTS_LIST_LOG_METHOD, SUCCESS_MESSAGE_ACCOUNT_MOVEMENTS_RETRIEVED))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, EXECUTE_CUSTOMER_MOVEMENTS_LIST_LOG_METHOD, throwable.getMessage()));
    }


}