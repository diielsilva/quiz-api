package com.diel.dev.quiz.game.repositories;

import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class GameRepository {
    private final Map<String, Game> database = new HashMap<>();

    public Game store(Game toStore) {
        String id = UUID.randomUUID().toString();

        Game stored = new Game(
                toStore.id().isEmpty() ? id : toStore.id(),
                toStore.status(),
                toStore.current(),
                toStore.remaining(),
                toStore.lives()
        );

        database.put(stored.id(), stored);

        return stored;
    }

    public Game retrieve(String id) {
        Game stored = database.get(id);

        if (stored == null) {
            throw new EntityNotFoundException(String.format("Game: %s not found!", id));
        }

        return stored;
    }

    public void remove(String id) {
        Game stored = retrieve(id);

        database.remove(stored.id());
    }

}
