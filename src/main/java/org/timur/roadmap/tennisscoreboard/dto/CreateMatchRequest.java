package org.timur.roadmap.tennisscoreboard.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMatchRequest(
        @NotBlank
        String firstPlayerName,

        @NotBlank
        String secondPlayerName
) {
}