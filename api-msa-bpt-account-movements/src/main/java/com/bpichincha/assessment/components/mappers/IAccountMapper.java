package com.bpichincha.assessment.components.mappers;

import com.bpichincha.assessment.entities.Account;
import com.bpichincha.assessment.entities.Customer;
import com.bpichincha.assessment.entities.Movement;
import io.r2dbc.spi.Row;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface IAccountMapper {

    @Mapping(target = "id", expression = "java(row.get(\"accountId\", java.lang.Long.class))")
    @Mapping(target = "accountNumber", expression = "java(row.get(\"account_number\", java.lang.String.class))")
    @Mapping(target = "accountType", expression = "java(row.get(\"account_type\", java.lang.String.class))")
    @Mapping(target = "status", expression = "java(Boolean.TRUE.equals(row.get(\"status\", java.lang.Boolean.class)))")
    @Mapping(target = "currentBalance", expression = "java(row.get(\"current_balance\", java.math.BigDecimal.class))")
    Account mapToAccount(Row row);

    // map row to Movement
    @Mapping(target = "id", expression = "java(row.get(\"movementId\", java.lang.Long.class))")
    @Mapping(target = "message", expression = "java(row.get(\"message\", java.lang.String.class))")
    @Mapping(target = "movementType", expression = "java((row.get(\"movementType\", java.lang.String.class)))")
    @Mapping(target = "amount", expression = "java(row.get(\"amount\", java.math.BigDecimal.class))")
    @Mapping(target = "initialBalance", expression = "java(row.get(\"initialBalance\", java.math.BigDecimal.class))")
    @Mapping(target = "transactionDate", expression = "java(row.get(\"transactionDate\", java.util.Date.class))")
    @Mapping(target = "movementStatus", expression = "java(row.get(\"movementStatus\", java.lang.String.class))")
    Movement mapToMovement(Row row);

    @Mapping(target = "id", expression = "java(row.get(\"customerId\", java.lang.Long.class))")
    @Mapping(target = "identification", expression = "java(row.get(\"identification\", java.lang.String.class))")
    @Mapping(target = "name", expression = "java(row.get(\"name\", java.lang.String.class))")
    @Mapping(target = "gender", expression = "java(row.get(\"gender\", java.lang.String.class))")
    @Mapping(target = "age", expression = "java(row.get(\"age\", java.lang.Integer.class))")
    @Mapping(target = "address", expression = "java(row.get(\"address\", java.lang.String.class))")
    @Mapping(target = "phone", expression = "java(row.get(\"phone\", java.lang.String.class))")
    @Mapping(target = "membershipNumber", expression = "java(row.get(\"membershipNumber\", java.lang.String.class))")
    Customer mapToCustomer(Row row);


}