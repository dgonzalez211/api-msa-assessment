package com.technical.assessment.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SqlQueryConstants {

    public static final String SQL_QUERY_FIND_CUSTOMER_BY_IDENTIFICATION = "SELECT c.id AS customerId, c.password, c.membership_number as membershipNumber, c.status, " +
            "p.id AS personId, p.identification, p.name, p.gender, p.age, p.address, p.phone " +
            "FROM customer c " +
            "LEFT JOIN person p ON c.id = p.id " +
            "WHERE c.status = true AND p.identification = :identification";

    public static final String SQL_QUERY_FIND_ALL_CUSTOMERS = "SELECT c.id AS customerId, c.password, c.membership_number as membershipNumber, c.status, " +
            "p.id AS personId, p.identification, p.name, p.gender, p.age, p.address, p.phone " +
            "FROM customer c " +
            "LEFT JOIN person p ON c.id = p.id " +
            "WHERE c.status = true";

}
