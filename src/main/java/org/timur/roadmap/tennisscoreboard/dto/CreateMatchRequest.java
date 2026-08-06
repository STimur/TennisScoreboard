package org.timur.roadmap.tennisscoreboard.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMatchRequest(
        @NotBlank(message = "Имя первого игрока не должно быть пустым")
        String firstPlayerName,

        @NotBlank
        String secondPlayerName
) {
}