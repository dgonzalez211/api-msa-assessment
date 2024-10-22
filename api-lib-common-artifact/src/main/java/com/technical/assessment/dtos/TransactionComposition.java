package com.technical.assessment.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionComposition<T extends Serializable, R extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5308116696846251146L;

    private T transactionRequest;
    private R transactionResponse;
}
