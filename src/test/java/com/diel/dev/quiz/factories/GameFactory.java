package com.diel.dev.quiz.factories;

import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.entities.Question;
import com.diel.dev.quiz.game.enums.GameStatus;

import java.util.List;

public abstract class GameFactory {

    public static Game getEmpty() {
        return new Game(
                "",
                GameStatus.PLAYING,
                new Question(0, "", List.of(), 0),
                List.of(),
                0
        );
    }
}
