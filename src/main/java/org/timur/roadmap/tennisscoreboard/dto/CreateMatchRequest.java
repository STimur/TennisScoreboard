package org.timur.roadmap.tennisscoreboard.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record CreateMatchRequest(

        // TODO: Нет ограничений на длину имени.
            // Это делает правила валидации в приложении неоднородными:
            // здесь ограничение отсутствует, а в БД (файл миграции и JPA Entity) — есть.
            // Правила валидации должны быть едины.
            // Важно проверять все необходимые ограничения на входе данных в приложение.

        @NotBlank(message = "Имя первого игрока не должно быть пустым")
        String firstPlayerName,

        @NotBlank(message = "Имя второго игрока не должно быть пустым")
        String secondPlayerName
) {
        @AssertTrue(message = "Имена игроков не могут совпадать")
        public boolean isPlayersAreDifferent() {

                // Лучше сравнивать без учёта регистра, чтобы написание "Петя" и "петя" считалось одним именем.
                return firstPlayerName == null || !firstPlayerName.equals(secondPlayerName);
        }
}