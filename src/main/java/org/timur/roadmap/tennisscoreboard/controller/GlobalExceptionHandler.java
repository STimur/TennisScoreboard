package org.timur.roadmap.tennisscoreboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.timur.roadmap.tennisscoreboard.dto.ErrorResponse;
import org.timur.roadmap.tennisscoreboard.dto.ValidationError;
import org.timur.roadmap.tennisscoreboard.dto.ValidationErrorResponse;
import org.timur.roadmap.tennisscoreboard.exception.DataAccessException;
import org.timur.roadmap.tennisscoreboard.exception.MatchNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO: Набор обрабатываемых исключений неполный.
        // Стоит добавить обработку и других возможных исключений, в том числе общий обработчик
        // для всех непредвиденных ошибок (Exception), чтобы клиент никогда не получал stack trace.

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMatchNotFound(MatchNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // TODO: Фронтенд ожидает в JSON одно поле с сообщением об ошибке, а не массив.
        // Поэтому сообщения об ошибке валидации имени сейчас не отображаются.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<ValidationError> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.add(new ValidationError(
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                );

        exception.getBindingResult()
                .getGlobalErrors()
                .forEach(error ->
                        errors.add(new ValidationError(
                                null,
                                error.getDefaultMessage()
                        ))
                );

        ValidationErrorResponse validationResponse = new ValidationErrorResponse(errors);
        ErrorResponse errorResponse = new ErrorResponse(validationResponse);

        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exception) {

        // TODO: Сообщение из DataAccessException может содержать внутренние детали реализации
            // (например, названия полей, структуру данных), которые не стоит показывать клиенту.
            // Сейчас это не так только из-за захардкоженного сообщения в DataAccessException.
            // Лучше возвращать обобщённое сообщение (например, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
            // а детали логировать.
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
