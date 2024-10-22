package com.technical.assessment.dtos;

import com.technical.assessment.dtos.requests.MovementTransactionRequestDto;
import com.technical.assessment.dtos.responses.MovementResultInformationDto;
import com.technical.assessment.entities.Movement;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountMovementTransactionComposition extends TransactionComposition<MovementTransactionRequestDto, MovementResultInformationDto> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5230901019196076399L;
    
    private Movement movement;
}