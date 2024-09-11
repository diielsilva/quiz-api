package com.diel.dev.quiz.game.entities;

import com.diel.dev.quiz.game.enums.GameStatus;

import java.util.List;

public record Game(
        String id,
        GameStatus status,
        Question current,
        List<Question> remaining,
        int lives
) {
}
