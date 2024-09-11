package com.diel.dev.quiz.game.repositories;

import com.diel.dev.quiz.game.entities.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository repository;

    @Test
    void retrieve_ShouldReturnQuestions() {
        List<Question> questions = repository.retrieve();

        assertAll(() -> {
            assertEquals(5, questions.size());
            assertEquals(4, questions.getLast().index());
        });
    }

}