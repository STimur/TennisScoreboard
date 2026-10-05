package org.timur.roadmap.tennisscoreboard.dto;

public record PlayerScoreDto(
        String name,
        String points,
        Integer games, // Это значение не может быть (не должно) null, поэтому можно использовать примитивный тип int
        Integer sets, // Это значение не может быть (не должно) null, поэтому можно использовать примитивный тип int
        Integer tieBreakPoints
) {
}
