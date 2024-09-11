package com.diel.dev.quiz.game.services;

import com.diel.dev.quiz.game.entities.Attempt;
import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.entities.Question;
import com.diel.dev.quiz.game.enums.GameStatus;
import com.diel.dev.quiz.game.repositories.GameRepository;
import com.diel.dev.quiz.game.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final QuestionRepository questionRepository;

    public GameService(GameRepository gameRepository, QuestionRepository questionRepository) {
        this.gameRepository = gameRepository;
        this.questionRepository = questionRepository;
    }

    public Game start() {
        List<Question> questions = questionRepository.retrieve();
        int index = getRandomIndex(questions);
        Question current = questions.get(index);
        int lives = 5;

        Game toStore = new Game(
                "",
                GameStatus.PLAYING,
                current,
                questions,
                lives
        );

        return gameRepository.store(toStore);
    }

    public Game play(Attempt attempt) {
        Game game = gameRepository.retrieve(attempt.gameId());
        boolean isAttemptCorrect = attempt.answer() == game.current().answer();

        if (!isAttemptCorrect) {
            int lives = game.lives() - 1;

            if (lives == 0) {
                Game toRemove = new Game(
                        game.id(),
                        GameStatus.FINISHED,
                        game.current(),
                        game.remaining(),
                        lives
                );

                gameRepository.remove(toRemove.id());

                return toRemove;
            }

            Game toUpdate = new Game(
                    game.id(),
                    game.status(),
                    game.current(),
                    game.remaining(),
                    lives
            );

            return gameRepository.store(toUpdate);
        }

        List<Question> questions = game.remaining();
        int questionIndex = game.current().index();
        questions.remove(questionIndex);

        boolean hasRemainingQuestions = !questions.isEmpty();

        if (!hasRemainingQuestions) {
            Game toRemove = new Game(
                    game.id(),
                    GameStatus.FINISHED,
                    game.current(),
                    questions,
                    game.lives()
            );

            gameRepository.remove(toRemove.id());

            return toRemove;
        }

        List<Question> sorted = sort(questions);
        int randomIndex = getRandomIndex(sorted);
        Question current = sorted.get(randomIndex);

        Game toUpdate = new Game(
                game.id(),
                game.status(),
                current,
                sorted,
                game.lives()
        );

        return gameRepository.store(toUpdate);
    }

    private List<Question> sort(List<Question> questions) {
        List<Question> sorted = new ArrayList<>();

        for (int index = 0; index < questions.size(); index++) {
            Question previous = questions.get(index);

            Question question = new Question(
                    index,
                    previous.title(),
                    previous.options(),
                    previous.answer()
            );

            sorted.add(question);
        }

        return sorted;
    }

    private int getRandomIndex(List<Question> questions) {
        Random randomizer = new Random();
        int min = 0;
        int max = questions.size();

        return randomizer.nextInt(max - min) + min;
    }

}
