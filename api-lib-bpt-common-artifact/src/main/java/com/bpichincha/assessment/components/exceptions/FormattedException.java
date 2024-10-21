package com.bpichincha.assessment.components.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.List;

@Getter
public class FormattedException extends RuntimeException {

    private final transient HttpStatus httpStatus;
    private final transient String responseCode;

    public FormattedException(String message, HttpStatus httpStatus, String responseCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
    }

    public FormattedException(String message, Throwable cause, HttpStatus httpStatus, String responseCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
    }

    public FormattedException(String message, HttpStatus httpStatus, String responseCode, Object... parameters) {
        super(formatExceptionMessage(message, List.of(parameters)));
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
    }

    public FormattedException(String message, List<Object> parameters, HttpStatus httpStatus, String responseCode) {
        super(formatExceptionMessage(message, parameters));
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
    }

    private static String formatExceptionMessage(String message, List<Object> parameters) {
        return MessageFormat.format(message, parameters.toArray());
    }
}
