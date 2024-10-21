package com.bpichincha.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ValidationConstants {

    public static final String BYPASS_SONAR_FIELD_UNCHECKED = "unchecked";
    public static final String PROCESS_VALIDATION_CURRENT_FIELD_VALUE = "currentField";
    public static final String PROCESS_VALIDATION_ERROR_NOT_NULL_FORMAT = "The currentField must not be null";
    public static final String PROCESS_VALIDATION_ERROR_NOT_BLANK_FORMAT = "The currentField must not be blank";
    public static final String PROCESS_VALIDATION_ERROR_NOT_EMPTY_FORMAT = "The currentField must not be empty";
    public static final String PROCESS_VALIDATION_ERROR_MAX_VALUE_FORMAT = "The currentField is greater than allowed";
    public static final String PROCESS_VALIDATION_ERROR_MIN_VALUE_FORMAT = "The currentField in less than allowed";
}