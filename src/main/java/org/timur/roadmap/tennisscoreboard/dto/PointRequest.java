package org.timur.roadmap.tennisscoreboard.dto;

import jakarta.validation.constraints.NotBlank;

public record PointRequest(
        @NotBlank
        String name
) {
}