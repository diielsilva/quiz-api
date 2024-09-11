package com.diel.dev.quiz.factories;

import com.diel.dev.quiz.game.entities.Attempt;
import com.diel.dev.quiz.game.entities.Game;

public abstract class AttemptFactory {

    public static Attempt getCorrect(Game game) {
        return new Attempt(
                game.id(),
                game.current().answer()
        );
    }

    public static Attempt getIncorrect(Game game) {
        return new Attempt(
                game.id(),
                game.current().answer() + 1
        );
    }

}
