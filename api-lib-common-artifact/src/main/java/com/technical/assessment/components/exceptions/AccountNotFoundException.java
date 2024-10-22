package com.technical.assessment.components.exceptions;


import com.technical.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends FormattedException {

    public AccountNotFoundException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.ACCOUNT_NOT_FOUND, parameters);
    }

}
