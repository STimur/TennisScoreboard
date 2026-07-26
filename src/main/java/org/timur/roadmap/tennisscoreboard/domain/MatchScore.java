package org.timur.roadmap.tennisscoreboard.domain;

public class MatchScore {

    private final String firstPlayerName;
    private final String secondPlayerName;
    private int firstPlayerSets;
    private int secondPlayerSets;
    private SetScore currentSetScore;
    private boolean isFinished;
    private String winnerName;

    public MatchScore(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = 0;
        this.secondPlayerSets = 0;
        this.currentSetScore = new SetScore();
        isFinished = false;
        winnerName = null;
    }

    public MatchScore(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                      int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                      GamePoint firstPlayersPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSetScore = new SetScore(firstPlayerGames, secondPlayerGames, firstPlayersPoints, secondPlayerPoints);
        isFinished = false;
        winnerName = null;
    }

    public MatchScore(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                      int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                      int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSetScore = new SetScore(firstPlayerGames, secondPlayerGames,
                firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
        isFinished = false;
        winnerName = null;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayer() {
        return secondPlayerName;
    }

    public void addPoint(String playerName) {
        if (playerName.equals(firstPlayerName)) {
            currentSetScore.addFirstPlayerPoint();
            if (currentSetScore.isFinished()) {
                firstPlayerSets++;
                if (firstPlayerSets == 2) {
                    winnerName = firstPlayerName;
                    isFinished = true;
                    return;
                }
                currentSetScore = new SetScore();
            }
        } else {
            currentSetScore.addSecondPlayerPoint();
            if (currentSetScore.isFinished()) {
                secondPlayerSets++;
                if (secondPlayerSets == 2) {
                    winnerName = secondPlayerName;
                    isFinished = true;
                    return;
                }
                currentSetScore = new SetScore();
            }
        }
    }

    public String getFirstPlayerPoints() {
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSetScore.getFirstPlayerPoints();
    }

    public Integer getFirstPlayerGames() {
        if (isFinished)
            return null;

        return currentSetScore.getFirstPlayerGames();
    }

    public int getFirstPlayerSets() {
        return firstPlayerSets;
    }

    public Integer getFirstPlayerTieBreakPoints() {
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSetScore.getFirstPlayerPoints());
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public String getSecondPlayerPoints() {
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSetScore.getSecondPlayerPoints();
    }

    public Integer getSecondPlayerGames() {
        if (isFinished)
            return null;

        return currentSetScore.getSecondPlayerGames();
    }

    public int getSecondPlayerSets() {
        return secondPlayerSets;
    }

    public Integer getSecondPlayerTieBreakPoints() {
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSetScore.getSecondPlayerPoints());
    }

    public String getWinnerName() {
        return winnerName;
    }

    private boolean isTieBreakInProgress() {
        return currentSetScore.isTieBreakInProgress();
    }
}
