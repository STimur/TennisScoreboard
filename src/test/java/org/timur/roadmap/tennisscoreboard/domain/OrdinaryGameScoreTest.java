package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdinaryGameScoreTest {

    // Для тестов доменной модели построение состояния через публичный API ценнее, чем установка его через конструктор.
        // Так проще отловить ошибку на пути от одного состояния к другому.
        // Поэтому для создания модели стоит использовать конструктор без аргументов
        // и приводить объект в нужное для каждого теста состояние через публичные методы.

    // Непоследовательное именование тестов.
        // Смешаны простой описательный стиль (initialGameState), should-style и Given/When/Then.
        // Стоит выбрать один подход и придерживаться его. Для тестов модели хорошо подходит GWT стиль.

    // Можно добавить тесты для покрытия полного розыгрыша гейма, а также для крайних случаев и негативных кейсов.
        // Например, для попытки начислить очко в уже завершённом гейме.

    @Test
    void initialGameState() {
        GameScore gameScore = new OrdinaryGameScore();

        assertEquals(GamePoint.LOVE.toString(), gameScore.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), gameScore.getSecondPlayerPoints());
        assertNull(gameScore.getWinner());
        assertFalse(gameScore.isFinished());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        GameScore gameScore = new OrdinaryGameScore();

        gameScore.addFirstPlayerPoint();
        assertEquals(GamePoint.FIFTEEN.toString(), gameScore.getFirstPlayerPoints());
        gameScore.addFirstPlayerPoint();
        assertEquals(GamePoint.THIRTY.toString(), gameScore.getFirstPlayerPoints());
        gameScore.addFirstPlayerPoint();
        assertEquals(GamePoint.FORTY.toString(), gameScore.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        GameScore gameScore = new OrdinaryGameScore();

        gameScore.addSecondPlayerPoint();
        assertEquals(GamePoint.FIFTEEN.toString(), gameScore.getSecondPlayerPoints());
        gameScore.addSecondPlayerPoint();
        assertEquals(GamePoint.THIRTY.toString(), gameScore.getSecondPlayerPoints());
        gameScore.addSecondPlayerPoint();
        assertEquals(GamePoint.FORTY.toString(), gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenBothPlayersHaveForty_whenFirstPlayerScores_shouldHaveAdvantage() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.FORTY, GamePoint.FORTY);

        gameScore.addFirstPlayerPoint();

        assertEquals(GamePoint.AD.toString(), gameScore.getFirstPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasAdvantage_whenFirstPlayerScores_shouldBecomeFortyForty() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.FORTY, GamePoint.AD);

        gameScore.addFirstPlayerPoint();

        assertEquals(GamePoint.FORTY.toString(), gameScore.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasThirtyOrLess_whenFirstPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.FORTY, GamePoint.THIRTY);

        gameScore.addFirstPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.FIRST, gameScore.getWinner());
    }

    @Test
    void givenFirstPlayerHasAdvantage_whenFirstPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.AD, GamePoint.FORTY);

        gameScore.addFirstPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.FIRST, gameScore.getWinner());
    }

    @Test
    void givenBothPlayersHaveForty_whenSecondPlayerScores_shouldHaveAdvantage() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.FORTY, GamePoint.FORTY);

        gameScore.addSecondPlayerPoint();

        assertEquals(GamePoint.AD.toString(), gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasAdvantage_whenSecondPlayerScores_shouldBecomeFortyForty() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.AD, GamePoint.FORTY);

        gameScore.addSecondPlayerPoint();

        assertEquals(GamePoint.FORTY.toString(), gameScore.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasThirtyOrLess_whenSecondPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.THIRTY, GamePoint.FORTY);

        gameScore.addSecondPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.SECOND, gameScore.getWinner());
    }

    @Test
    void givenSecondPlayerHasAdvantage_whenSecondPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new OrdinaryGameScore(GamePoint.FORTY, GamePoint.AD);

        gameScore.addSecondPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.SECOND, gameScore.getWinner());
    }
}