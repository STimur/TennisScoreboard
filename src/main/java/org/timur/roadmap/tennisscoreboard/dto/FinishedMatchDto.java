package org.timur.roadmap.tennisscoreboard.dto;

public record FinishedMatchDto(
        String firstPlayerName,
        String secondPlayerName,
        String winnerName
) {
}