package org.timur.roadmap.tennisscoreboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MatchTest {

    @Test
    public void something() {
        Match match = new Match("First Player", "Second Player");

        match.addPoint("First Player");

        assertEquals("First Player", match.getFirstPlayerName());
        assertEquals(GamePoint.FIFTEEN.toString(), match.getFirstPlayerPoints());
        assertEquals(0, match.getFirstPlayerGames());
        assertEquals(0, match.getFirstPlayerSets());
        assertNull(match.getFirstPlayerTieBreakPoints());

        assertEquals("Second Player", match.getSecondPlayerName());
        assertEquals(GamePoint.LOVE.toString(), match.getSecondPlayerPoints());
        assertEquals(0, match.getSecondPlayerGames());
        assertEquals(0, match.getSecondPlayerSets());
        assertNull(match.getSecondPlayerTieBreakPoints());

        assertNull(match.getWinnerName());
    }
}
