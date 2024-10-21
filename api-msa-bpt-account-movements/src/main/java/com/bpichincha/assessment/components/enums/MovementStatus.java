package com.bpichincha.assessment.components.enums;

import com.fasterxml.jackson.core.JsonToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum MovementStatus {
    APPROVED(Boolean.TRUE, "APPROVED"),
    FAILED(Boolean.FALSE, "FAILED");

    private final Boolean code;
    private final String name;
}
