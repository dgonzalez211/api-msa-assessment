package com.bpichincha.assessment.components.helper;

import com.bpichincha.assessment.components.enums.ValidationProcessResult;
import com.bpichincha.assessment.constants.ValidationConstants;
import com.bpichincha.assessment.dtos.TransactionValidationResult;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

@Log4j2
@UtilityClass
public class TransactionValidationHelper {

    public static TransactionValidationResult validateMandatoryFields(final Object instanceToValidate){

        final TransactionValidationResult transNetworkValidationResult = TransactionValidationResult.builder()
            .validationProcessResult(ValidationProcessResult.PROCESS_SUCCESSFULLY_COMPLETED)
            .errors(new ArrayList<>())
            .build();

        final Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        final Set<ConstraintViolation<Object>> violations = validator.validate(instanceToValidate);
        if (!violations.isEmpty()) {
            for (var violation : violations) {
                String errorMessage = violation.getMessage().replace(ValidationConstants.PROCESS_VALIDATION_CURRENT_FIELD_VALUE, violation.getPropertyPath().toString());
                transNetworkValidationResult.getErrors().add(errorMessage);
            }
        }

        if (!transNetworkValidationResult.getErrors().isEmpty()) {
            transNetworkValidationResult.setValidationProcessResult(ValidationProcessResult.PROCESS_FAILED);
        }

        return transNetworkValidationResult;
    }
}