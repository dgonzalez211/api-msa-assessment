package com.bpichincha.assessment.components.exceptions;


import com.bpichincha.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class TransactionInformationNotFoundException extends FormattedException {

    public TransactionInformationNotFoundException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.ACCOUNT_NOT_FOUND, parameters);
    }

}
