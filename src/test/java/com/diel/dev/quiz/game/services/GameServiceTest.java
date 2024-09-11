package com.diel.dev.quiz.game.services;

import com.diel.dev.quiz.factories.AttemptFactory;
import com.diel.dev.quiz.game.entities.Attempt;
import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.enums.GameStatus;
import com.diel.dev.quiz.game.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService service;

    @Test
    void start_ShouldStartGame() {
        Game started = service.start();

        assertAll(() -> {
            assertNotEquals("", started.id());
            assertNotNull(started.current());
            assertEquals(5, started.remaining().size());
            assertEquals(5, started.lives());
        });
    }

    @Test
    void play_ShouldDecrementLives_WhenAnswerIsIncorrect() {
        Game created = service.start();

        Attempt incorrect = AttemptFactory.getIncorrect(created);

        Game current = service.play(incorrect);

        assertAll(() -> {
            assertEquals(4, current.lives());
            assertEquals(created.current(), current.current());
        });
    }

    @Test
    void play_ShouldFinishAndRemoveGame_WhenDoNotHaveRemainingLivesAndAnswerIsIncorrect() {
        Game created = service.start();

        Attempt incorrect = AttemptFactory.getIncorrect(created);

        for (int index = 0; index < 4; index++) {
            service.play(incorrect);
        }

        Game finished = service.play(incorrect);

        assertAll(() -> {
            assertEquals(finished.current(), created.current());
            assertEquals(GameStatus.FINISHED, finished.status());
            assertEquals(0, finished.lives());
            assertThrows(EntityNotFoundException.class, () -> service.play(incorrect));
        });
    }

    @Test
    void play_ShouldDecrementRemainingQuestions_WhenAnswerIsCorrect() {
        Game created = service.start();

        Attempt correct = AttemptFactory.getCorrect(created);

        Game current = service.play(correct);

        assertAll(() -> {
            assertEquals(4, current.remaining().size());
            assertEquals(3, current.remaining().getLast().index());
            assertNotEquals(created.current(), current.current());
        });
    }

    @Test
    void play_ShouldFinishAndRemoveGame_WhenQuestionsWereAnswered() {
        Game created = service.start();

        Attempt correct = AttemptFactory.getCorrect(created);

        Game current = service.play(correct);

        for (int index = 0; index < 3; index++) {
            correct = AttemptFactory.getCorrect(current);
            current = service.play(correct);
        }

        correct = AttemptFactory.getCorrect(current);

        Game last = service.play(correct);

        assertAll(() -> {
            assertEquals(GameStatus.FINISHED, last.status());
            assertTrue(last.remaining().isEmpty());
            assertEquals(5, last.lives());
        });
    }
}