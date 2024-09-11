package com.diel.dev.quiz.shared.handlers;

import com.diel.dev.quiz.game.exceptions.EntityNotFoundException;
import com.diel.dev.quiz.shared.dtos.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception) {
        ApiError error = new ApiError(
                404,
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(error);
    }

}
