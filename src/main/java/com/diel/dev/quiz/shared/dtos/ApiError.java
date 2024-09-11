package com.diel.dev.quiz.shared.dtos;

public record ApiError(
        int status,
        String message
) {
}
