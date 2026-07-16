package org.timur.roadmap.tennisscoreboard.service;

import org.junit.jupiter.api.Test;
import org.timur.roadmap.tennisscoreboard.domain.Game;
import org.timur.roadmap.tennisscoreboard.domain.GamePoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    @Test
    void initialGameState() {
        Game game = new Game();

        assertEquals(GamePoint.LOVE, game.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE, game.getSecondPlayerPoints());
        assertEquals(0, game.getWinner());
        assertFalse(game.isFinished());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        Game game = new Game();

        game.addFirstPlayerPoint();
        assertEquals(GamePoint.FIFTEEN, game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals(GamePoint.THIRTY, game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals(GamePoint.FORTY, game.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        Game game = new Game();

        game.addSecondPlayerPoint();
        assertEquals(GamePoint.FIFTEEN, game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals(GamePoint.THIRTY, game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals(GamePoint.FORTY, game.getSecondPlayerPoints());
    }

    @Test
    void givenBothPlayersHaveForty_whenFirstPlayerScores_shouldHaveAdvantage() {
        Game game = new Game(GamePoint.FORTY, GamePoint.FORTY);

        game.addFirstPlayerPoint();

        assertEquals(GamePoint.AD, game.getFirstPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasAdvantage_whenFirstPlayerScores_shouldBecomeFortyForty() {
        Game game = new Game(GamePoint.FORTY, GamePoint.AD);

        game.addFirstPlayerPoint();

        assertEquals(GamePoint.FORTY, game.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY, game.getSecondPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasThirtyOrLess_whenFirstPlayerScores_shouldBecomeWinner() {
        Game game = new Game(GamePoint.FORTY, GamePoint.THIRTY);

        game.addFirstPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(1, game.getWinner());
    }

    @Test
    void givenBothPlayersHaveForty_whenSecondPlayerScores_shouldHaveAdvantage() {
        Game game = new Game(GamePoint.FORTY, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertEquals(GamePoint.AD, game.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasAdvantage_whenSecondPlayerScores_shouldBecomeFortyForty() {
        Game game = new Game(GamePoint.AD, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertEquals(GamePoint.FORTY, game.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY, game.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasThirtyOrLess_whenSecondPlayerScores_shouldBecomeWinner() {
        Game game = new Game(GamePoint.THIRTY, GamePoint.FORTY);

        game.addSecondPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(2, game.getWinner());
    }
}