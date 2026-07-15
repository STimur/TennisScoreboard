package org.timur.roadmap.tennisscoreboard.service;

import org.junit.jupiter.api.Test;
import org.timur.roadmap.tennisscoreboard.domain.Game;
import org.timur.roadmap.tennisscoreboard.domain.GamePoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void initialPointsShouldBeZeroForBothPlayers() {
        Game game = new Game();

        assertEquals(GamePoint.LOVE, game.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE, game.getSecondPlayerPoints());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        Game game = new Game();

        game.addFirstPlayerPoint();

        assertEquals(GamePoint.FIFTEEN, game.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        Game game = new Game();

        game.addSecondPlayerPoint();

        assertEquals(GamePoint.FIFTEEN, game.getSecondPlayerPoints());
    }
}