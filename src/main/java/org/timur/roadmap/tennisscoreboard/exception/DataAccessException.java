package org.timur.roadmap.tennisscoreboard.exception;

public class DataAccessException extends RuntimeException {

    // Сообщения в исключениях принято писать на английском языке.
    // Эта константа может быть private (использование в тестах не оправдывает её публичность).
    public static final String MESSAGE = "Ошибка при обращении к базе данных";

    public DataAccessException(Throwable cause) {

        // Можно передавать в super сообщение из оригинального исключения
        super(MESSAGE, cause);
    }
}
