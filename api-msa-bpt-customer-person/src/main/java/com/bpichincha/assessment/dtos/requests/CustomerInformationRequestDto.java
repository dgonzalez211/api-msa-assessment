package com.bpichincha.assessment.dtos.requests;

import com.bpichincha.assessment.constants.DataInformationConstants;
import com.bpichincha.assessment.constants.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInformationRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3104671865801048673L;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER)
    private String identification;
}