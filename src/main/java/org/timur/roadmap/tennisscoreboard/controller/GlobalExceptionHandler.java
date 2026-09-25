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

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMatchNotFound(MatchNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
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

        return ResponseEntity
                .badRequest()
                .body(new ValidationErrorResponse(errors));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
