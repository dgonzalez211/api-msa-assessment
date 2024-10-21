package com.bpichincha.assessment.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntegrationResponse<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2933982575148748674L;

    private String rc;
    private String msg;
    private HttpStatus httpStatus;
    private T data;
}