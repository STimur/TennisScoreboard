package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TieBreakTest {

    @Test
    void initialTieBreakState() {
        Game game = new TieBreak(0, 0);

        assertEquals("0", game.getFirstPlayerPoints());
        assertEquals("0", game.getSecondPlayerPoints());
        assertNull(game.getWinner());
        assertFalse(game.isFinished());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        Game game = new TieBreak(0, 0);

        game.addFirstPlayerPoint();
        assertEquals("1", game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals("2", game.getFirstPlayerPoints());
        game.addFirstPlayerPoint();
        assertEquals("3", game.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        Game game = new TieBreak(0, 0);

        game.addSecondPlayerPoint();
        assertEquals("1", game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals("2", game.getSecondPlayerPoints());
        game.addSecondPlayerPoint();
        assertEquals("3", game.getSecondPlayerPoints());
    }

    @Test
    void givenBothPlayersHaveSix_whenFirstPlayerScores_shouldHaveSeven() {
        Game game = new TieBreak(6, 6);

        game.addFirstPlayerPoint();

        assertEquals("7", game.getFirstPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasSeven_whenFirstPlayerScores_shouldBecomeSevenSeven() {
        Game game = new TieBreak(6, 7);

        game.addFirstPlayerPoint();

        assertEquals("7", game.getFirstPlayerPoints());
        assertEquals("7", game.getSecondPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasFiveOrLess_whenFirstPlayerHasSixAndScores_shouldBecomeWinner() {
        Game game = new TieBreak(6, 5);

        game.addFirstPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void givenFirstPlayerHasSevenAndSecondSixOrLess_whenFirstPlayerScores_shouldBecomeWinner() {
        Game game = new TieBreak(7, 6);

        game.addFirstPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void givenBothPlayersHaveSix_whenSecondPlayerScores_shouldHaveSeven() {
        Game game = new TieBreak(6, 6);

        game.addSecondPlayerPoint();

        assertEquals("7", game.getSecondPlayerPoints());
        assertFalse(game.isFinished());
    }

    @Test
    void givenFirstPlayerHasSeven_whenSecondPlayerHasSixAndScores_shouldBecomeSevenSeven() {
        Game game = new TieBreak(7, 6);

        game.addSecondPlayerPoint();

        assertEquals("7", game.getFirstPlayerPoints());
        assertEquals("7", game.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasFiveOrLess_whenSecondPlayerHasSixAndScores_shouldBecomeWinner() {
        Game game = new TieBreak(3, 6);

        game.addSecondPlayerPoint();

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    @Test
    void givenSecondPlayerHasEightAndFirstSevenOrLess_whenSecondPlayerScores_shouldBecomeWinner() {
        Game game = new TieBreak(7, 8);

        game.addSecondPlayerPoint();

        assertEquals("7", game.getFirstPlayerPoints());
        assertEquals("9", game.getSecondPlayerPoints());
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }
}