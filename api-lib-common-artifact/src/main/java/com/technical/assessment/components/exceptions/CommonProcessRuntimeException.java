package com.technical.assessment.components.exceptions;

import com.technical.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class CommonProcessRuntimeException extends FormattedException {

    public CommonProcessRuntimeException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.COMMON_BUSINESS_EXCEPTION, parameters);
    }
}
