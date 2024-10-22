package com.technical.assessment.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 2865410745382518392L;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String membershipNumber;

    @Column(nullable = false)
    private Boolean status;
}
