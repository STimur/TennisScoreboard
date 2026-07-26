package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MatchScoreTest {

    @Test
    public void firstPlayerScoresPoint() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 0, 0, GamePoint.FORTY, GamePoint.AD);

        matchScore.addPoint("First Player");

        assertEquals(GamePoint.FORTY.toString(), matchScore.getFirstPlayerPoints());
        assertNull(matchScore.getFirstPlayerTieBreakPoints());
        assertEquals(GamePoint.FORTY.toString(), matchScore.getSecondPlayerPoints());
        assertNull(matchScore.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void secondPlayerScoresPoint() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 0, 0, GamePoint.FIFTEEN, GamePoint.THIRTY);

        matchScore.addPoint("Second Player");

        assertEquals(GamePoint.FIFTEEN.toString(), matchScore.getFirstPlayerPoints());
        assertEquals(GamePoint.FORTY.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerScoresGame() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 2, 2, GamePoint.FORTY, GamePoint.THIRTY);

        matchScore.addPoint("First Player");

        assertEquals(3, matchScore.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresGame() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 2, 2, GamePoint.FORTY, GamePoint.AD);

        matchScore.addPoint("Second Player");

        assertEquals(3, matchScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerGotToTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 5, 6, GamePoint.FORTY, GamePoint.THIRTY);

        matchScore.addPoint("First Player");
        matchScore.addPoint("First Player");

        assertEquals(6, matchScore.getFirstPlayerGames());
        assertNull(matchScore.getFirstPlayerPoints());
        assertEquals(1, matchScore.getFirstPlayerTieBreakPoints());
        assertEquals(6, matchScore.getSecondPlayerGames());
        assertNull(matchScore.getSecondPlayerPoints());
        assertEquals(0, matchScore.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void secondPlayerGotToTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 6, 5, GamePoint.FORTY, GamePoint.AD);

        matchScore.addPoint("Second Player");
        matchScore.addPoint("Second Player");

        assertEquals(6, matchScore.getFirstPlayerGames());
        assertEquals(null, matchScore.getFirstPlayerPoints());
        assertEquals(0, matchScore.getFirstPlayerTieBreakPoints());
        assertEquals(6, matchScore.getSecondPlayerGames());
        assertEquals(null, matchScore.getSecondPlayerPoints());
        assertEquals(1, matchScore.getSecondPlayerTieBreakPoints());
    }

    @Test
    public void firstPlayerScoresSet() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 5, 4, GamePoint.FORTY, GamePoint.LOVE);

        matchScore.addPoint("First Player");

        assertEquals(1, matchScore.getFirstPlayerSets());
        assertEquals(0, matchScore.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());

        assertEquals(0, matchScore.getSecondPlayerSets());
        assertEquals(0, matchScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresSet() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 3, 5, GamePoint.FORTY, GamePoint.AD);

        matchScore.addPoint("Second Player");

        assertEquals(0, matchScore.getFirstPlayerSets());
        assertEquals(0, matchScore.getFirstPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());

        assertEquals(1, matchScore.getSecondPlayerSets());
        assertEquals(0, matchScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerScoresSetInTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 6, 6, 6, 5);

        matchScore.addPoint("First Player");

        assertEquals(1, matchScore.getFirstPlayerSets());
        assertEquals(0, matchScore.getFirstPlayerGames());
        assertEquals(0, matchScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void secondPlayerScoresSetInTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 0, 6, 6, 7, 8);

        matchScore.addPoint("Second Player");

        assertEquals(1, matchScore.getSecondPlayerSets());
        assertEquals(0, matchScore.getFirstPlayerGames());
        assertEquals(0, matchScore.getSecondPlayerGames());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getFirstPlayerPoints());
        assertEquals(GamePoint.LOVE.toString(), matchScore.getSecondPlayerPoints());
    }

    @Test
    public void firstPlayerWinsMatch() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 1, 1, 5, 2, GamePoint.FORTY, GamePoint.THIRTY);

        matchScore.addPoint("First Player");

        assertEquals("First Player", matchScore.getFirstPlayerName());
        assertEquals(2, matchScore.getFirstPlayerSets());
        assertNull(matchScore.getFirstPlayerGames());
        assertNull(matchScore.getFirstPlayerPoints());
        assertNull(matchScore.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getSecondPlayerName());
        assertEquals(1, matchScore.getSecondPlayerSets());
        assertNull(matchScore.getSecondPlayerGames());
        assertNull(matchScore.getSecondPlayerPoints());
        assertNull(matchScore.getSecondPlayerTieBreakPoints());

        assertEquals("First Player", matchScore.getWinnerName());
    }

    @Test
    public void secondPlayerWinsMatch() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 0, 1, 5, 6, GamePoint.FORTY, GamePoint.AD);

        matchScore.addPoint("Second Player");

        assertEquals("First Player", matchScore.getFirstPlayerName());
        assertEquals(0, matchScore.getFirstPlayerSets());
        assertNull(matchScore.getFirstPlayerGames());
        assertNull(matchScore.getFirstPlayerPoints());
        assertNull(matchScore.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getSecondPlayerName());
        assertEquals(2, matchScore.getSecondPlayerSets());
        assertNull(matchScore.getSecondPlayerGames());
        assertNull(matchScore.getSecondPlayerPoints());
        assertNull(matchScore.getSecondPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getWinnerName());
    }

    @Test
    public void firstPlayerWinsMatchInTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 1, 1, 6, 6, 6, 5);

        matchScore.addPoint("First Player");

        assertEquals("First Player", matchScore.getFirstPlayerName());
        assertEquals(2, matchScore.getFirstPlayerSets());
        assertNull(matchScore.getFirstPlayerGames());
        assertNull(matchScore.getFirstPlayerPoints());
        assertNull(matchScore.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getSecondPlayerName());
        assertEquals(1, matchScore.getSecondPlayerSets());
        assertNull(matchScore.getSecondPlayerGames());
        assertNull(matchScore.getSecondPlayerPoints());
        assertNull(matchScore.getSecondPlayerTieBreakPoints());

        assertEquals("First Player", matchScore.getWinnerName());
    }

    @Test
    public void secondPlayerWinsMatchInTieBreak() {
        MatchScore matchScore = new MatchScore("First Player", "Second Player", 1, 1, 6, 6, 8, 9);

        matchScore.addPoint("Second Player");

        assertEquals("First Player", matchScore.getFirstPlayerName());
        assertEquals(1, matchScore.getFirstPlayerSets());
        assertNull(matchScore.getFirstPlayerGames());
        assertNull(matchScore.getFirstPlayerPoints());
        assertNull(matchScore.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getSecondPlayerName());
        assertEquals(2, matchScore.getSecondPlayerSets());
        assertNull(matchScore.getSecondPlayerGames());
        assertNull(matchScore.getSecondPlayerPoints());
        assertNull(matchScore.getSecondPlayerTieBreakPoints());

        assertEquals("Second Player", matchScore.getWinnerName());
    }
}
