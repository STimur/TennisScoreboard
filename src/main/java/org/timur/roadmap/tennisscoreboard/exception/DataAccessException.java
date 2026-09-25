package org.timur.roadmap.tennisscoreboard.exception;

public class DataAccessException extends RuntimeException {

    public static final String MESSAGE = "Ошибка при обращении к базе данных";

    public DataAccessException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
