package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TieBreakScoreTest {

    @Test
    void initialTieBreakState() {
        GameScore gameScore = new TieBreakScore(0, 0);

        assertEquals("0", gameScore.getFirstPlayerPoints());
        assertEquals("0", gameScore.getSecondPlayerPoints());
        assertNull(gameScore.getWinner());
        assertFalse(gameScore.isFinished());
    }

    @Test
    void shouldAddFirstPlayerPoint() {
        GameScore gameScore = new TieBreakScore(0, 0);

        gameScore.addFirstPlayerPoint();
        assertEquals("1", gameScore.getFirstPlayerPoints());
        gameScore.addFirstPlayerPoint();
        assertEquals("2", gameScore.getFirstPlayerPoints());
        gameScore.addFirstPlayerPoint();
        assertEquals("3", gameScore.getFirstPlayerPoints());
    }

    @Test
    void shouldAddSecondPlayerPoint() {
        GameScore gameScore = new TieBreakScore(0, 0);

        gameScore.addSecondPlayerPoint();
        assertEquals("1", gameScore.getSecondPlayerPoints());
        gameScore.addSecondPlayerPoint();
        assertEquals("2", gameScore.getSecondPlayerPoints());
        gameScore.addSecondPlayerPoint();
        assertEquals("3", gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenBothPlayersHaveSix_whenFirstPlayerScores_shouldHaveSeven() {
        GameScore gameScore = new TieBreakScore(6, 6);

        gameScore.addFirstPlayerPoint();

        assertEquals("7", gameScore.getFirstPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasSeven_whenFirstPlayerScores_shouldBecomeSevenSeven() {
        GameScore gameScore = new TieBreakScore(6, 7);

        gameScore.addFirstPlayerPoint();

        assertEquals("7", gameScore.getFirstPlayerPoints());
        assertEquals("7", gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenSecondPlayerHasFiveOrLess_whenFirstPlayerHasSixAndScores_shouldBecomeWinner() {
        GameScore gameScore = new TieBreakScore(6, 5);

        gameScore.addFirstPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.FIRST, gameScore.getWinner());
    }

    @Test
    void givenFirstPlayerHasSevenAndSecondSixOrLess_whenFirstPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new TieBreakScore(7, 6);

        gameScore.addFirstPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.FIRST, gameScore.getWinner());
    }

    @Test
    void givenBothPlayersHaveSix_whenSecondPlayerScores_shouldHaveSeven() {
        GameScore gameScore = new TieBreakScore(6, 6);

        gameScore.addSecondPlayerPoint();

        assertEquals("7", gameScore.getSecondPlayerPoints());
        assertFalse(gameScore.isFinished());
    }

    @Test
    void givenFirstPlayerHasSeven_whenSecondPlayerHasSixAndScores_shouldBecomeSevenSeven() {
        GameScore gameScore = new TieBreakScore(7, 6);

        gameScore.addSecondPlayerPoint();

        assertEquals("7", gameScore.getFirstPlayerPoints());
        assertEquals("7", gameScore.getSecondPlayerPoints());
    }

    @Test
    void givenFirstPlayerHasFiveOrLess_whenSecondPlayerHasSixAndScores_shouldBecomeWinner() {
        GameScore gameScore = new TieBreakScore(3, 6);

        gameScore.addSecondPlayerPoint();

        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.SECOND, gameScore.getWinner());
    }

    @Test
    void givenSecondPlayerHasEightAndFirstSevenOrLess_whenSecondPlayerScores_shouldBecomeWinner() {
        GameScore gameScore = new TieBreakScore(7, 8);

        gameScore.addSecondPlayerPoint();

        assertEquals("7", gameScore.getFirstPlayerPoints());
        assertEquals("9", gameScore.getSecondPlayerPoints());
        assertTrue(gameScore.isFinished());
        assertEquals(PlayerSide.SECOND, gameScore.getWinner());
    }
}