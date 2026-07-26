package org.timur.roadmap.tennisscoreboard.dto;

public record PlayerScoreDto(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
