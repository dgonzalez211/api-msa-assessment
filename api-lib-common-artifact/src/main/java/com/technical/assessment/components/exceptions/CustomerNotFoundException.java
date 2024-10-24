package com.technical.assessment.components.exceptions;


import com.technical.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends FormattedException {

    public CustomerNotFoundException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.CUSTOMER_NOT_FOUND, parameters);
    }

}
