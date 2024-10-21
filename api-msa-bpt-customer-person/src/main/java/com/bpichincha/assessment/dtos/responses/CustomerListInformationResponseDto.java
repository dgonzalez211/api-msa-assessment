package com.bpichincha.assessment.dtos.responses;

import com.bpichincha.assessment.constants.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerListInformationResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5777013430038774115L;

    @NotNull(message = ValidationConstants.PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT)
    private transient List<CustomerInformationResponseDto> customers;


}