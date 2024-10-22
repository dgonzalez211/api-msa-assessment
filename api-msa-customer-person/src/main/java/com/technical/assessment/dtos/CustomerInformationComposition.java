package com.technical.assessment.dtos;

import com.technical.assessment.dtos.requests.CustomerInformationRequestDto;
import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.entities.Customer;
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
public class CustomerInformationComposition extends TransactionComposition<CustomerInformationRequestDto, CustomerInformationResponseDto> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5230901019196076399L;

    private Customer customerEntity;
}