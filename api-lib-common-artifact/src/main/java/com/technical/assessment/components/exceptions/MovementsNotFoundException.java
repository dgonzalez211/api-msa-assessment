package com.technical.assessment.components.exceptions;


import com.technical.assessment.constants.ResponseCodes;
import org.springframework.http.HttpStatus;

public class MovementsNotFoundException extends FormattedException {

    public MovementsNotFoundException(String message, Object... parameters) {
        super(message, HttpStatus.BAD_REQUEST, ResponseCodes.MOVEMENTS_NOT_FOUND, parameters);
    }

}
