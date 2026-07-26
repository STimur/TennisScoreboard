package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MatchTest {

    @Test
    public void firstPlayerScoresPoint() {
        Match match = new Match("First Player", "Second Player", 0, 0, 0, 0, GamePoint.FORTY, GamePoint.AD);

        match.addPoint("First Player");

        assertEquals(GamePoint.FORTY.toString(), match.getFirstPlayerPoints());
        assertNull(match.getFirstPlayerTieBreakPoints());
        assertEquals(GamePoint.FORTY.toString(), match.getSecondPlayerPoints());
        assertNull(match.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void secondPlayerScoresPoint() {
        Match match = new Match("First Player", "Second Player", 0, 0, 0, 0, GamePoint.FIFTEEN, GamePoint.THIRTY);

        match.addPoint("Second Player");

        assertEquals(GamePoint.FIFTEEN.toString(), match.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerScoresGame() {
        Match match = new Match("First Player", "Second Player", 0, 0, 2, 2, GamePoint.FORTY, GamePoint.THIRTY);

        match.addPoint("First Player");

        assertEquals(3, match.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresGame() {
        Match match = new Match("First Player", "Second Player", 0, 0, 2, 2, GamePoint.FORTY, GamePoint.AD);

        match.addPoint("Second Player");

        assertEquals(3, match.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerGotToTieBreak() {
        Match match = new Match("First Player", "Second Player", 0, 0, 5, 6, GamePoint.FORTY, GamePoint.THIRTY);

        match.addPoint("First Player");
        match.addPoint("First Player");

        assertEquals(6, match.getFirstPlayerGames());
        assertNull(match.getFirstPlayerPoints());
        assertEquals(1, match.getFirstPlayerTieBreakPoints());
        assertEquals(6, match.getSecondPlayerGames());
        assertNull(match.getSecondPlayerPoints());
        assertEquals(0, match.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void secondPlayerGotToTieBreak() {
        Match match = new Match("First Player", "Second Player", 0, 0, 6, 5, GamePoint.FORTY, GamePoint.AD);

        match.addPoint("Second Player");
        match.addPoint("Second Player");

        assertEquals(6, match.getFirstPlayerGames());
        assertEquals(null, match.getFirstPlayerPoints());
        assertEquals(0, match.getFirstPlayerTieBreakPoints());
        assertEquals(6, match.getSecondPlayerGames());
        assertEquals(null, match.getSecondPlayerPoints());
        assertEquals(1, match.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void firstPlayerScoresSet() {
        Match match = new Match("First Player", "Second Player", 0, 0, 5, 4, GamePoint.FORTY, GamePoint.LOVE);

        match.addPoint("First Player");

        assertEquals(1, match.getFirstPlayerSets());
        assertEquals(0, match.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());

        assertEquals(0, match.getSecondPlayerSets());
        assertEquals(0, match.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresSet() {
        Match match = new Match("First Player", "Second Player", 0, 0, 3, 5, GamePoint.FORTY, GamePoint.AD);

        match.addPoint("Second Player");

        assertEquals(0, match.getFirstPlayerSets());
        assertEquals(0, match.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());

        assertEquals(1, match.getSecondPlayerSets());
        assertEquals(0, match.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerScoresSetInTieBreak() {
        Match match = new Match("First Player", "Second Player", 0, 0, 6, 6, 6, 5);

        match.addPoint("First Player");

        assertEquals(1, match.getFirstPlayerSets());
        assertEquals(0, match.getFirstPlayerGames());
        assertEquals(0, match.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresSetInTieBreak() {
        Match match = new Match("First Player", "Second Player", 0, 0, 6, 6, 7, 8);

        match.addPoint("Second Player");

        assertEquals(1, match.getSecondPlayerSets());
        assertEquals(0, match.getFirstPlayerGames());
        assertEquals(0, match.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), match.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerWinsMatch() {
        Match match = new Match("First Player", "Second Player", 1, 1, 5, 2, GamePoint.FORTY, GamePoint.THIRTY);

        match.addPoint("First Player");

        assertEquals("First Player", match.getFirstPlayerName());
        assertEquals(2, match.getFirstPlayerSets());
        assertNull(match.getFirstPlayerGames());
        assertNull(match.getFirstPlayerPoints());
        assertNull(match.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", match.getSecondPlayerName());
        assertEquals(1, match.getSecondPlayerSets());
        assertNull(match.getSecondPlayerGames());
        assertNull(match.getSecondPlayerPoints());
        assertNull(match.getSecondPlayerTieBreakPoints());

        assertEquals("First Player", match.getWinnerName());
    }

    @Test
    public void secondPlayerWinsMatch() {
        Match match = new Match("First Player", "Second Player", 0, 1, 5, 6, GamePoint.FORTY, GamePoint.AD);

        match.addPoint("Second Player");

        assertEquals("First Player", match.getFirstPlayerName());
        assertEquals(0, match.getFirstPlayerSets());
        assertNull(match.getFirstPlayerGames());
        assertNull(match.getFirstPlayerPoints());
        assertNull(match.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", match.getSecondPlayerName());
        assertEquals(2, match.getSecondPlayerSets());
        assertNull(match.getSecondPlayerGames());
        assertNull(match.getSecondPlayerPoints());
        assertNull(match.getSecondPlayerTieBreakPoints());

        assertEquals("Second Player", match.getWinnerName());
    }

    @Test
    public void firstPlayerWinsMatchInTieBreak() {
        Match match = new Match("First Player", "Second Player", 1, 1, 6, 6, 6, 5);

        match.addPoint("First Player");

        assertEquals("First Player", match.getFirstPlayerName());
        assertEquals(2, match.getFirstPlayerSets());
        assertNull(match.getFirstPlayerGames());
        assertNull(match.getFirstPlayerPoints());
        assertNull(match.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", match.getSecondPlayerName());
        assertEquals(1, match.getSecondPlayerSets());
        assertNull(match.getSecondPlayerGames());
        assertNull(match.getSecondPlayerPoints());
        assertNull(match.getSecondPlayerTieBreakPoints());

        assertEquals("First Player", match.getWinnerName());
    }

    @Test
    public void secondPlayerWinsMatchInTieBreak() {
        Match match = new Match("First Player", "Second Player", 1, 1, 6, 6, 8, 9);

        match.addPoint("Second Player");

        assertEquals("First Player", match.getFirstPlayerName());
        assertEquals(1, match.getFirstPlayerSets());
        assertNull(match.getFirstPlayerGames());
        assertNull(match.getFirstPlayerPoints());
        assertNull(match.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", match.getSecondPlayerName());
        assertEquals(2, match.getSecondPlayerSets());
        assertNull(match.getSecondPlayerGames());
        assertNull(match.getSecondPlayerPoints());
        assertNull(match.getSecondPlayerTieBreakPoints());

        assertEquals("Second Player", match.getWinnerName());
    }
}
