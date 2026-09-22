package org.timur.roadmap.tennisscoreboard.dto;

public record ValidationError(
        String field,
        String message
) {
}
