package com.bpichincha.assessment.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movement extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2154283518263921549L;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "origin_account_id", nullable = false)
    private Account originAccount;

    @Column(nullable = false)
    private BigDecimal initialBalance;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String movementType;

    @Column(nullable = false)
    private String movementStatus;

    @Column(nullable = false)
    private String message;
}
