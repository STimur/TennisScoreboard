package org.timur.roadmap.tennisscoreboard.dto;

import java.util.List;

public record ValidationErrorResponse(
        List<ValidationError> errors
) {
}
