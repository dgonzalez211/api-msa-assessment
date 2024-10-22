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

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInformationResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5777013430038774115L;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_MEMBERSHIP_NUMBER)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_MEMBERSHIP_NUMBER)
    private String membershipNumber;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_IDENTIFICATION_NUMBER)
    private String identification;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_NAME)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_NAME)
    private String name;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_GENDER)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_GENDER)
    private String gender;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_AGE)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_AGE)
    private Integer age;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_ADDRESS)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_ADDRESS)
    private String address;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotBlank(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT)
    @JsonProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_PHONE)
    @JsonbProperty(DataInformationConstants.CUSTOMER_REQUEST_INTEGRATION_FIELD_PHONE)
    private String phone;


}