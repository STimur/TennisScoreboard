package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetScoreTest {

    // Для тестов доменной модели построение состояния через публичный API ценнее, чем установка его через конструктор.
        // Так проще отловить ошибку на пути от одного состояния к другому.
        // Поэтому для создания модели стоит использовать конструктор без аргументов
        // и приводить объект в нужное для каждого теста состояние через публичные методы.

    // Непоследовательное именование тестов.
        // Смешаны простой описательный стиль (initialSetState, firstPlayerScoresGame) и Given/When/Then (усечённый).
        // Стоит выбрать один подход и придерживаться его. Для тестов модели хорошо подходит GWT стиль.

    // Можно добавить тесты для покрытия полного розыгрыша сета, а также для крайних случаев и негативных кейсов.
        // Например, для попытки начислить очко в уже завершённом сете.

    // Чтобы задать более человекочитаемые имена тестам можно использовать @DisplayName.

    @Test
    public void initialSetState() {
        SetScore setScore = new SetScore();

        setScore.addFirstPlayerPoint();

        assertEquals(0, setScore.getFirstPlayerGames());
        assertEquals(GamePoint.FIFTEEN.toString(), setScore.getFirstPlayerPoints());
        assertEquals(0, setScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), setScore.getSecondPlayerPoints());
        assertFalse(setScore.isFinished());
    }

    @Test
    public void firstPlayerScoresGame() {
        SetScore setScore = new SetScore(GamePoint.FORTY, GamePoint.LOVE);

        setScore.addFirstPlayerPoint();

        assertEquals(1, setScore.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), setScore.getFirstPlayerPoints());
        assertEquals(0, setScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), setScore.getSecondPlayerPoints());
        assertFalse(setScore.isFinished());
    }

    @Test
    public void secondPlayerScoresGame() {
        SetScore setScore = new SetScore(GamePoint.FORTY, GamePoint.AD);

        setScore.addSecondPlayerPoint();

        assertEquals(0, setScore.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), setScore.getFirstPlayerPoints());
        assertEquals(1, setScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), setScore.getSecondPlayerPoints());
        assertFalse(setScore.isFinished());
    }

    @Test
    public void firstPlayerWinsSet() {
        SetScore setScore = new SetScore(5, 0, GamePoint.FORTY, GamePoint.FIFTEEN);

        setScore.addFirstPlayerPoint();

        assertEquals(6, setScore.getFirstPlayerGames());
        assertEquals(0, setScore.getSecondPlayerGames());
        assertTrue(setScore.isFinished());
        assertEquals(PlayerSide.FIRST, setScore.getWinner());
    }

    @Test
    public void whenFirstPlayerGotToSixSixScoreTieBreakIsPlayed() {
        SetScore setScore = new SetScore(5, 6, GamePoint.AD, GamePoint.FORTY);

        setScore.addFirstPlayerPoint();
        setScore.addFirstPlayerPoint();

        assertEquals("1", setScore.getFirstPlayerPoints());
        assertEquals("0", setScore.getSecondPlayerPoints());
        assertFalse(setScore.isFinished());
        assertNull(setScore.getWinner());
    }

    @Test
    public void secondPlayerWinsSet() {
        SetScore setScore = new SetScore(5, 6, GamePoint.THIRTY, GamePoint.FORTY);

        setScore.addSecondPlayerPoint();

        assertEquals(5, setScore.getFirstPlayerGames());
        assertEquals(7, setScore.getSecondPlayerGames());
        assertTrue(setScore.isFinished());
        assertEquals(PlayerSide.SECOND, setScore.getWinner());
    }

    @Test
    public void whenSecondPlayerGotToSixSixScoreTieBreakIsPlayed() {
        SetScore setScore = new SetScore(6, 5, GamePoint.FIFTEEN, GamePoint.FORTY);

        setScore.addSecondPlayerPoint();
        setScore.addSecondPlayerPoint();

        assertEquals("0", setScore.getFirstPlayerPoints());
        assertEquals("1", setScore.getSecondPlayerPoints());
        assertFalse(setScore.isFinished());
        assertNull(setScore.getWinner());
    }

    @Test
    public void firstPlayerWinsTieBreak() {
        SetScore setScore = new SetScore(6, 6, 9, 8);

        setScore.addFirstPlayerPoint();

        assertEquals(7, setScore.getFirstPlayerGames());
        assertEquals(6, setScore.getSecondPlayerGames());
        assertTrue(setScore.isFinished());
        assertEquals(PlayerSide.FIRST, setScore.getWinner());
    }

    @Test
    public void secondPlayerWinsTieBreak() {
        SetScore setScore = new SetScore(6, 6, 10, 11);

        setScore.addSecondPlayerPoint();

        assertEquals(6, setScore.getFirstPlayerGames());
        assertEquals(7, setScore.getSecondPlayerGames());
        assertTrue(setScore.isFinished());
        assertEquals(PlayerSide.SECOND, setScore.getWinner());
    }
}
