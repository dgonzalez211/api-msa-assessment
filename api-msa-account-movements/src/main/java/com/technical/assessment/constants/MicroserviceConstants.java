package com.technical.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MicroserviceConstants {

    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String MICROSERVICE_ACCOUNTS_PATH = MICROSERVICE_PATH_CONTEXT + "/accounts";
    public static final String MICROSERVICE_MOVEMENTS_PATH = MICROSERVICE_PATH_CONTEXT + "/movements";
    public static final String MICROSERVICE_MOVEMENTS_TRANSACTION_EXCHANGE_PATH = MICROSERVICE_PATH_CONTEXT + "/transaction/exchange";
    public static final String MICROSERVICE_REPORTS_PATH = MICROSERVICE_PATH_CONTEXT + "/reports";

    public static final String MOVEMENT_TRANSACTION_ID = "MOVEMENT_TRANSACTION_ID";

    public static final String ERROR_MESSAGE_CUSTOMER_NOT_FOUND = "Customer {0} not found or not active";
    public static final String ERROR_MESSAGE_NO_CUSTOMERS_FOUND = "There is no active customers found";
    public static final String ERROR_MESSAGE_ACCOUNT_NOT_FOUND = "Account not found: {0}";
    public static final String ERROR_MESSAGE_ACCOUNT_MOVEMENTS_NOT_FOUND = "Account movements not found for account: {0}";
    public static final String ERROR_MESSAGE_REQUEST_INFORMATION_NOT_FOUND = "The request information was not found with associated data: {0}";

    public static final String SUCCESS_MESSAGE_CUSTOMER_FOUND = "Customer found: {}";
    public static final String SUCCESS_MESSAGE_ACCOUNT_MOVEMENTS_RETRIEVED = "All account movements retrieved with success";
    public static final String SUCCESS_MESSAGE_CUSTOMERS_RETRIEVED = "All customers retrieved with success";


}
