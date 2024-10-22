package com.technical.assessment.dtos.responses;

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
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementResultInformationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5777013430038774115L;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_TRANSACTION_DATE)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_TRANSACTION_DATE)
    private Date transactionDate;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_AMOUNT)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_AMOUNT)
    private BigDecimal amount;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_ORIGIN_ACCOUNT_NUMBER)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_ORIGIN_ACCOUNT_NUMBER)
    private String originAccountNumber;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_MESSAGE)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_MESSAGE)
    private String message;


}