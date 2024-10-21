package com.bpichincha.assessment.dtos.responses;

import com.bpichincha.assessment.constants.DataInformationConstants;
import com.bpichincha.assessment.constants.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementListInformationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5777013430038774115L;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    @NotEmpty(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_EMPTY_FORMAT)
    @JsonProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_MOVEMENTS)
    @JsonbProperty(DataInformationConstants.MOVEMENT_REQUEST_INTEGRATION_FIELD_MOVEMENTS)
    private List<MovementResultInformationDto> movements;


}