package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdinaryGameTest {

    @Test
    void initialGameState() {
        Game game = new OrdinaryGame();

        assertEquals(GamePoint.LOVE.toString(), game.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), game.getSecondPlayerPoints());
        assertNull(game.getWinner());
        assertFalse(game.isFinished());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        Game game = new OrdinaryGame();

        game.addFirstPlayerPoint();
        assertEquals(GamePoint.FIFTEEN.toString(), game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals(GamePoint.THIRTY.toString(), game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals(GamePoint.FORTY.toString(), game.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        Game game = new OrdinaryGame();

        game.addSecondPlayerPoint();
        assertEquals(GamePoint.FIFTEEN.toString(), game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals(GamePoint.THIRTY.toString(), game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals(GamePoint.FORTY.toString(), game.getSecondPlayerPoints());
    }

    @Test
    void givenBothPlayersHaveForty_whenFirstPlayerScores_shouldHaveAdvantage() {
        Game game = new OrdinaryGame(GamePoint.FORTY, GamePoint.FORTY);

        game.addFirstPlayerPoint();

        assertEquals(GamePoint.AD.toString(), game.getFirstPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasAdvantage_whenFirstPlayerScores_shouldBecomeFortyForty() {
        Game game = new OrdinaryGame(GamePoint.FORTY, GamePoint.AD);

        game.addFirstPlayerPoint();

        assertEquals(GamePoint.FORTY.toString(), game.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), game.getSecondPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasThirtyOrLess_whenFirstPlayerScores_shouldBecomeWinner() {
        Game game = new OrdinaryGame(GamePoint.FORTY, GamePoint.THIRTY);

        game.addFirstPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void givenFirstPlayerHasAdvantage_whenFirstPlayerScores_shouldBecomeWinner() {
        Game game = new OrdinaryGame(GamePoint.AD, GamePoint.FORTY);

        game.addFirstPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void givenBothPlayersHaveForty_whenSecondPlayerScores_shouldHaveAdvantage() {
        Game game = new OrdinaryGame(GamePoint.FORTY, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertEquals(GamePoint.AD.toString(), game.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasAdvantage_whenSecondPlayerScores_shouldBecomeFortyForty() {
        Game game = new OrdinaryGame(GamePoint.AD, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertEquals(GamePoint.FORTY.toString(), game.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), game.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasThirtyOrLess_whenSecondPlayerScores_shouldBecomeWinner() {
        Game game = new OrdinaryGame(GamePoint.THIRTY, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    @Test
    void givenSecondPlayerHasAdvantage_whenSecondPlayerScores_shouldBecomeWinner() {
        Game game = new OrdinaryGame(GamePoint.FORTY, GamePoint.AD);

        game.addSecondPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }
}