package com.technical.assessment.components.mappers;

import com.technical.assessment.dtos.responses.CustomerInformationResponseDto;
import com.technical.assessment.entities.Customer;
import io.r2dbc.spi.Row;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface ICustomerMapper {

    @Mapping(source = "membershipNumber", target = "membershipNumber")
    @Mapping(source = "identification", target = "identification")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    CustomerInformationResponseDto mapperToCustomerInformation(Customer customer);

    @Mapping(target = "id", expression = "java(row.get(\"customerId\", Long.class))")
    @Mapping(target = "identification", expression = "java(row.get(\"identification\", String.class))")
    @Mapping(target = "name", expression = "java(row.get(\"name\", String.class))")
    @Mapping(target = "gender", expression = "java(row.get(\"gender\", String.class))")
    @Mapping(target = "age", expression = "java(row.get(\"age\", Integer.class))")
    @Mapping(target = "address", expression = "java(row.get(\"address\", String.class))")
    @Mapping(target = "phone", expression = "java(row.get(\"phone\", String.class))")
    @Mapping(target = "membershipNumber", expression = "java(row.get(\"membershipNumber\", String.class))")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    Customer mapToCustomer(Row row);

}