package com.technical.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MicroserviceConstants {

    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String MICROSERVICE_CUSTOMERS_PATH = MICROSERVICE_PATH_CONTEXT + "/customers";
    public static final String MICROSERVICE_GET_CUSTOMER_INFORMATION_PATH = MICROSERVICE_PATH_CONTEXT + "/information";

    public static final String BEAN_CUSTOMER_TRANSACTION_ID = "CUSTOMER_TRANSACTION_ID";

    public static final String ERROR_MESSAGE_CUSTOMER_NOT_FOUND = "Customer {0} not found or not active";
    public static final String ERROR_MESSAGE_CUSTOMERS_NOT_FOUND = "There is no active customers";

    public static final String SUCCESS_MESSAGE_CUSTOMER_FOUND = "Customer found: {}";

}
