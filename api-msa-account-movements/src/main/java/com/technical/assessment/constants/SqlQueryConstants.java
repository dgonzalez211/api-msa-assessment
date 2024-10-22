package com.technical.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQueryConstants {

    public static final String SQL_QUERY_GET_ACCOUNT_CUSTOMER = "SELECT a.id as accountId, a.*, c.id AS customerId, c.password, c.membership_number as membershipNumber, c.status AS customerStatus " +
            "FROM account a " +
            "LEFT JOIN customer c ON a.customer_id = c.id " +
            "WHERE a.account_number = :accountNumber AND a.status = true";

    public static final String SQL_QUERY_GET_ACCOUNT_BY_ACCOUNT_NUMBER = "SELECT a.id as accountId, a.*, c.id as customerId, c.membership_number as membershipNumber, c.*, p.id as personId, p.* " +
            "FROM account a LEFT JOIN customer c ON a.customer_id = c.id " +
            "LEFT JOIN person p ON c.id = p.id " +
            "WHERE a.account_number = :accountNumber AND a.status = true AND c.status = true";

    public static final String SQL_QUERY_GET_CUSTOMER_BY_IDENTIFICATION = "SELECT c.id AS customerId, c.password, c.membership_number as membershipNumber, c.status, " +
            "p.id AS personId, p.identification, p.name, p.gender, p.age, p.address, p.phone " +
            "FROM customer c " +
            "LEFT JOIN person p ON c.id = p.id " +
            "WHERE c.status = true AND p.identification = :identification";

    public static final String SQL_QUERY_UPDATE_ACCOUNT_BALANCE = "UPDATE account SET current_balance = :balance WHERE id = :id";

    public static final String SQL_QUERY_INSERT_MOVEMENT = "INSERT INTO movement (transaction_date, origin_account_id, initial_balance, amount, movement_type, movement_status, message)" +
            " VALUES (:transaction_date, :origin_account_id, :initial_balance, :amount, :movement_type, :movement_status, :message)";

    public static final String SQL_QUERY_GET_ACCOUNT_MOVEMENTS = "SELECT m.id AS movementId, m.transaction_date as transactionDate, m.origin_account_id as originAccountId, m.initial_balance as initialBalance, " +
            "m.amount, m.movement_type as movementType, m.movement_status as movementStatus, m.message " +
            "FROM movement m LEFT JOIN customer c ON m.origin_account_id = c.id " +
            "WHERE c.status = true AND m.origin_account_id = :originAccountId";

    public static final String SQL_QUERY_GET_CUSTOMER_MOVEMENTS = "SELECT a.id as accountId, a.*, m.id AS movementId, m.transaction_date as transactionDate, m.origin_account_id as originAccountId, m.initial_balance as initialBalance, " +
            "m.amount, m.movement_type as movementType, m.movement_status as movementStatus, m.message " +
            "FROM movement m " +
            "LEFT JOIN account a ON m.origin_account_id = a.id " +
            "WHERE a.customer_id = :customerId";
}
