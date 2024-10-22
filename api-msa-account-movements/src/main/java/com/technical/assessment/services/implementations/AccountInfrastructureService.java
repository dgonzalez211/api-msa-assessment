package com.technical.assessment.services.implementations;

import com.technical.assessment.components.enums.MovementStatus;
import com.technical.assessment.constants.LoggerConstants;
import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.IntegrationResponse;
import com.technical.assessment.dtos.responses.MovementListInformationDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.entities.Account;
import com.technical.assessment.entities.Movement;
import com.technical.assessment.repository.contracts.AccountReactiveRepository;
import com.technical.assessment.services.contracts.IMovementInfrastructureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.technical.assessment.constants.ProcessConstants.PROCESS_RETRIEVE_TRANSACTION_INFORMATION;

@Slf4j
@Service
@AllArgsConstructor
public class AccountInfrastructureService implements IMovementInfrastructureService {

    AccountReactiveRepository repository;

    @Override
    public Mono<MovementResultInformationDto> doOnExecuteAccountMovementTransaction(Movement movement) {
        return Mono.just(movement).<Movement>handle((currentMovement, sink) -> {
                    if (currentMovement.getOriginAccount().getCurrentBalance().compareTo(movement.getAmount()) < 0) {
                        sink.error(new IllegalArgumentException("Origin account does not have enough balance to make the transaction"));
                        return;
                    }
                    sink.next(currentMovement);
                })
                .map(currentMovement -> {
                    Account originAccount = currentMovement.getOriginAccount();
                    originAccount.setCurrentBalance(originAccount.getCurrentBalance().add(currentMovement.getAmount()));
                    return currentMovement;
                })
                .flatMap(currentMovement -> repository.doOnExecuteAccountMovement(currentMovement))
                .flatMap(movementResult -> {
                    MovementResultInformationDto result = MovementResultInformationDto.builder()
                            .amount(movementResult.getAmount())
                            .transactionDate(movementResult.getTransactionDate())
                            .originAccountNumber(movementResult.getOriginAccount().getAccountNumber())
                            .message(MovementStatus.APPROVED.getName())
                            .build();
                    return Mono.just(result);
                })
                .doOnSuccess(success -> log.debug(LoggerConstants.COMMON_SUCCESS_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, success.toString()))
                .doOnError(throwable -> log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_FORMAT, PROCESS_RETRIEVE_TRANSACTION_INFORMATION, throwable.getMessage()));
    }

    @Override
    public Mono<Movement> doOnRetrieveTransactionCompositionInformation(MovementTransactionRequestDto request) {
        return repository.doOnRetrieveTransactionCompositionInformation(request);
    }

    @Override
    public Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveTransactionListInformation(String accountNumber) {
        return repository.doOnRetrieveAccountInformation(accountNumber)
                .flatMapMany(repository::doOnRetrieveTransactionListInformation)
                .map(movement -> MovementResultInformationDto.builder()
                        .amount(movement.getAmount())
                        .originAccountNumber(movement.getOriginAccount().getAccountNumber())
                        .message(movement.getMessage())
                        .transactionDate(movement.getTransactionDate())
                        .build())
                .collectList()
                .map(movements -> MovementListInformationDto.builder()
                        .movements(movements)
                        .build())
                .flatMap(movementListInformationDto -> {
                    IntegrationResponse<MovementListInformationDto> response = IntegrationResponse.<MovementListInformationDto>builder()
                            .rc(HttpStatus.OK.getReasonPhrase())
                            .msg("Transaction executed successfully")
                            .data(movementListInformationDto)
                            .build();
                    return Mono.just(response);
                });
    }

    @Override
    public Mono<IntegrationResponse<MovementListInformationDto>> doOnRetrieveCustomerMovementsListInformation(String customerIdentification) {
        return repository.doOnRetrieveCustomerInformation(customerIdentification)
                .flatMapMany(repository::doOnRetrieveCustomerMovementsListInformation)
                .map(movement -> MovementResultInformationDto.builder()
                        .amount(movement.getAmount())
                        .originAccountNumber(movement.getOriginAccount().getAccountNumber())
                        .message(movement.getMessage())
                        .transactionDate(movement.getTransactionDate())
                        .build())
                .collectList()
                .map(movements -> MovementListInformationDto.builder()
                        .movements(movements)
                        .build())
                .flatMap(movementListInformationDto -> {
                    IntegrationResponse<MovementListInformationDto> response = IntegrationResponse.<MovementListInformationDto>builder()
                            .rc(HttpStatus.OK.getReasonPhrase())
                            .msg("Transaction executed successfully")
                            .data(movementListInformationDto)
                            .build();
                    return Mono.just(response);
                });
    }

}
