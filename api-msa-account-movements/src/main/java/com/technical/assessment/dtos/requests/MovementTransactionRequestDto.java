package com.technical.assessment.dtos.requests;

import com.technical.assessment.constants.DataInformationConstants;
import com.technical.assessment.constants.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementTransactionRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3104671865801048673L;

    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_ORIGIN_ACCOUNT_NUMBER)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_ORIGIN_ACCOUNT_NUMBER)
    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    private String originAccountNumber;

    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_AMOUNT)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_AMOUNT)
    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    private BigDecimal amount;
}