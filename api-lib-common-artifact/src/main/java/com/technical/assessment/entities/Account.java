package com.technical.assessment.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2986976475987748672L;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private BigDecimal currentBalance;

    @Column(nullable = false)
    private String accountType;
}
