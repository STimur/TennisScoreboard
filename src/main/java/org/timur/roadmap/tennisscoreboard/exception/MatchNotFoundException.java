package org.timur.roadmap.tennisscoreboard.exception;

public class MatchNotFoundException extends RuntimeException {

    // Эта константа может быть private
    // Сообщения в исключениях принято писать на английском языке.
    public static final String MESSAGE = "Матч с таким uuid не найден";

    public MatchNotFoundException() {

        // В сообщении можно указывать id матча, чтобы было понятно, с каким конкретно id не найден матч.
        super(MESSAGE);
    }
}
