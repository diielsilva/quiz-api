package com.diel.dev.quiz.game.repositories;

import com.diel.dev.quiz.factories.GameFactory;
import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Test
    void store_ShouldStoreGame() {
        Game toStore = GameFactory.getEmpty();

        assertDoesNotThrow(() -> repository.store(toStore));
    }

    @Test
    void retrieve_ShouldReturnGame() {
        Game toStore = GameFactory.getEmpty();

        Game stored = repository.store(toStore);

        assertDoesNotThrow(() -> repository.retrieve(stored.id()));
    }

    @Test
    void retrieve_ShouldThrowsException_WhenGameWasNotFound() {
        assertThrows(EntityNotFoundException.class, () -> repository.retrieve(""));
    }

    @Test
    void remove_ShouldRemoveGame() {
        Game toStore = GameFactory.getEmpty();

        Game stored = repository.store(toStore);

        assertDoesNotThrow(() -> repository.remove(stored.id()));
    }
}