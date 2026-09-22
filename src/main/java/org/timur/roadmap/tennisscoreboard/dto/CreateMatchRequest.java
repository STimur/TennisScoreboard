package org.timur.roadmap.tennisscoreboard.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record CreateMatchRequest(
        @NotBlank(message = "Имя первого игрока не должно быть пустым")
        String firstPlayerName,

        @NotBlank(message = "Имя второго игрока не должно быть пустым")
        String secondPlayerName
) {
        @AssertTrue(message = "Имена игроков не могут совпадать")
        public boolean isPlayersAreDifferent() {
                return firstPlayerName == null || !firstPlayerName.equals(secondPlayerName);
        }
}