package com.bpichincha.assessment.components.exceptions;

import com.bpichincha.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class CommonProcessRuntimeException extends FormattedException {

    public CommonProcessRuntimeException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.COMMON_BUSINESS_EXCEPTION, parameters);
    }
}
