package com.bpichincha.assessment.dtos;

import com.bpichincha.assessment.components.enums.ValidationProcessResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionValidationResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 751809722188783883L;

    private ValidationProcessResult validationProcessResult;
    private List<String> errors;
}