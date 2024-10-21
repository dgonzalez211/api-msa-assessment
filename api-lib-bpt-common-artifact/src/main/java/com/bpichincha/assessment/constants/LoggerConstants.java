package com.bpichincha.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerConstants {

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_REQUEST_ID = "requestId";
    public static final String KEY_TENANT_ID = "tenantId";
    public static final String CONSTANTS_LOG_METHOD = "method";
    public static final String LOG_LEVEL = "log.level";
    public static final String HTTP_RESPONSE_FORMAT = "Response type [{}] from URL [{}] with state code [{}]";
    public static final String HTTP_WRITE_AND_FLUSH_WITH_FORMAT = "Response with state code[{}]";
    public static final String COMMON_ERROR_DELIMITER_FORMAT = " ; ";
    public static final String COMMON_ERROR_MESSAGE_STRING_FORMAT = "[ %s ]";

    public static final String COMMON_SUCCESS_DO_ON_PROCESS_FORMAT = "success process {}, response: {}";
    public static final String COMMON_ERROR_DO_ON_PROCESS_FORMAT = "error in process {}, error: {}";
    public static final String COMMON_ERROR_DO_ON_PROCESS_MULTIPLE_LINE_FORMAT = "error in process {}, errors: {}";
    public static final String COMMON_ERROR_DO_ON_PROCESS_ONE_LINE_FORMAT = "error in process %s, errors: ";
}
