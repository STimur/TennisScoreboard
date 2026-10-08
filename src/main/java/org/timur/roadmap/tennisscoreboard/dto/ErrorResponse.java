package org.timur.roadmap.tennisscoreboard.dto;

import java.util.stream.Collectors;

public record ErrorResponse(String message) {

    public ErrorResponse(ValidationErrorResponse validationResponse) {
        this(validationResponse.errors().stream()
                .map(ValidationError::message)
                .collect(Collectors.joining("; ")));
    }
}