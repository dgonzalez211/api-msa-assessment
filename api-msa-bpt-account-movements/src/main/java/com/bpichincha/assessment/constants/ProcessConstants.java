package com.bpichincha.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProcessConstants {
    
    public static final String PROCESS_EXECUTE_TRANSACTION = "doOnExecuteTransaction";
    public static final String PROCESS_LOAD_CONTEXT_CONFIGURATION = "doOnLoadContextConfiguration";
    public static final String PROCESS_VALIDATION_TRANSACTION_REQUEST = "doOnValidateTransactionRequest";
    public static final String PROCESS_RETRIEVE_TRANSACTION_INFORMATION = "doOnRetrieveTransactionInformation";
    public static final String PROCESS_VALIDATE_TRANSACTION_RESPONSE = "doOnValidateTransactionResponse";
    public static final String PROCESS_PROCESS_TRANSACTION_RESPONSE = "doOnProcessTransactionResponse";
    public static final String EXECUTE_MOVEMENT_TRANSACTION_LOG_METHOD = "doOnExecuteMovementTransaction";
    public static final String EXECUTE_ACCOUNT_MOVEMENTS_LOG_METHOD = "doOnExecuteAccountMovements";
    public static final String EXECUTE_CUSTOMER_ACCOUNTS_MOVEMENTS_LOG_METHOD = "doOnExecuteCustomerAccountsMovements";
    public static final String EXECUTE_CUSTOMER_MOVEMENTS_LIST_LOG_METHOD = "doOnRetrieveCustomerMovementsListInformation";
    public static final String EXECUTE_ACCOUNT_MOVEMENTS_LIST_LOG_METHOD = "doOnRetrieveTransactionListInformation";

}
