package com.diel.dev.quiz.game.entities;

import java.util.List;

public record Question(
        int index,
        String title,
        List<String> options,
        int answer
) {
}
