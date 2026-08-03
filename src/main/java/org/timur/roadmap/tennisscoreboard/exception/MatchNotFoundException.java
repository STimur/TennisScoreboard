package org.timur.roadmap.tennisscoreboard.exception;

public class MatchNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Матч с таким uuid не найден";

    public MatchNotFoundException() {
        super(MESSAGE);
    }
}
