package com.bpichincha.assessment.components.helper;

import com.bpichincha.assessment.constants.LoggerConstants;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.StringJoiner;

@Log4j2
@UtilityClass
public class TransactionLogErrorHelper {

    public static String getProcessErrors(String processName, final List<String> errors) {
        return formatErrors(processName, errors, LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_ONE_LINE_FORMAT);
    }

    public static void writeErrors(String processName, final List<String> errors) {
        log.error(formatErrors(processName, errors, LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_ONE_LINE_FORMAT));
    }

    public static void processErrorOnMultipleLines(String processName, final List<String> errors) {
        for (String error : errors) {
            log.error(LoggerConstants.COMMON_ERROR_DO_ON_PROCESS_MULTIPLE_LINE_FORMAT, processName, error);
        }
    }

    private static String formatErrors(String processName, final List<String> errors, String format) {
        final String processNameMessage = String.format(format, processName);
        final StringJoiner stringJoiner = new StringJoiner(LoggerConstants.COMMON_ERROR_DELIMITER_FORMAT);
        stringJoiner.add(processNameMessage);
        for (String error : errors) {
            stringJoiner.add(String.format(LoggerConstants.COMMON_ERROR_MESSAGE_STRING_FORMAT, error));
        }
        return stringJoiner.toString();
    }
}