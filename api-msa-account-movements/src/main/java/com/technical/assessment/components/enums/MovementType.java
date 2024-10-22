package com.technical.assessment.components.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum MovementType {
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL");

    private final String value;

    public static MovementType fromValue(String value) {
        return stream(values())
            .filter(movementType -> movementType.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
    }
}
