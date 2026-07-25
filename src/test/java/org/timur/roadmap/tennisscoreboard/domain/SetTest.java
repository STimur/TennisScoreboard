package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetTest {

    @Test
    public void initialSetState() {
        Set set = new Set();

        set.addFirstPlayerPoint();

        assertEquals(0, set.getFirstPlayerGames());
        assertEquals(GamePoint.FIFTEEN.toString(), set.getCurrentGame().getFirstPlayerPoints());
        assertEquals(0, set.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), set.getCurrentGame().getSecondPlayerPoints());
        assertFalse(set.isFinished());
    }

    @Test
    public void firstPlayerScoresGame() {
        Set set = new Set(GamePoint.FORTY, GamePoint.LOVE);

        set.addFirstPlayerPoint();

        assertEquals(1, set.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), set.getCurrentGame().getFirstPlayerPoints());
        assertEquals(0, set.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), set.getCurrentGame().getSecondPlayerPoints());
        assertFalse(set.isFinished());
    }

    @Test
    public void secondPlayerScoresGame() {
        Set set = new Set(GamePoint.FORTY, GamePoint.AD);

        set.addSecondPlayerPoint();

        assertEquals(0, set.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), set.getCurrentGame().getFirstPlayerPoints());
        assertEquals(1, set.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), set.getCurrentGame().getSecondPlayerPoints());
        assertFalse(set.isFinished());
    }

    @Test
    public void getCurrentGameThrowsExceptionWhenSetIsFinished() {
        Set set = new Set(5, 0, GamePoint.FORTY, GamePoint.FIFTEEN);

        set.addFirstPlayerPoint();

        assertThrows(Set.SetFinishedException.class, set::getCurrentGame);
    }

    @Test
    public void firstPlayerWinsSet() {
        Set set = new Set(5, 0, GamePoint.FORTY, GamePoint.FIFTEEN);

        set.addFirstPlayerPoint();

        assertEquals(6, set.getFirstPlayerGames());
        assertEquals(0, set.getSecondPlayerGames());
        assertTrue(set.isFinished());
    }

    @Test
    public void secondPlayerWinsSet() {
        Set set = new Set(5, 6, GamePoint.THIRTY, GamePoint.FORTY);

        set.addSecondPlayerPoint();

        assertEquals(5, set.getFirstPlayerGames());
        assertEquals(7, set.getSecondPlayerGames());
        assertTrue(set.isFinished());
    }

    @Test
    public void firstPlayerWinsTieBreak() {
        Set set = new Set(6, 6, 9, 8);

        set.addFirstPlayerPoint();

        assertEquals(7, set.getFirstPlayerGames());
        assertEquals(6, set.getSecondPlayerGames());
        assertTrue(set.isFinished());
    }

    @Test
    public void secondPlayerWinsTieBreak() {
        Set set = new Set(6, 6, 10, 11);

        set.addSecondPlayerPoint();

        assertEquals(6, set.getFirstPlayerGames());
        assertEquals(7, set.getSecondPlayerGames());
        assertTrue(set.isFinished());
    }
}
