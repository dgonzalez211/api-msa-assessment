package com.technical.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProcessConstants {
    
    public static final String PROCESS_EXECUTE_TRANSACTION = "doOnExecuteTransaction";
    public static final String PROCESS_LOAD_CONTEXT_CONFIGURATION = "doOnLoadContextConfiguration";
    public static final String PROCESS_VALIDATION_TRANSACTION_REQUEST = "doOnValidateTransactionRequest";
    public static final String PROCESS_RETRIEVE_TRANSACTION_INFORMATION = "doOnRetrieveTransactionInformation";
    public static final String PROCESS_VALIDATE_TRANSACTION_RESPONSE = "doOnValidateTransactionResponse";
    public static final String PROCESS_PROCESS_TRANSACTION_RESPONSE = "doOnProcessTransactionResponse";
    public static final String EXECUTE_CUSTOMER_INFORMATION_TRANSACTION = "doOnExecuteCustomerInformationTransaction";
    public static final String EXECUTE_CUSTOMERS_INFORMATION = "doOnExecuteCustomersInformation";
    public static final String EXECUTE_FIND_CUSTOMER_BY_IDENTIFICATION = "findCustomerByIdentification";
    public static final String EXECUTE_FIND_ALL_CUSTOMERS = "findAllCustomers";

}
