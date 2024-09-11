package com.diel.dev.quiz.game.controllers;

import com.diel.dev.quiz.game.entities.Attempt;
import com.diel.dev.quiz.game.entities.Game;
import com.diel.dev.quiz.game.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Game> start() {
        Game started = service.start();

        return ResponseEntity.ok(started);
    }

    @PatchMapping
    public ResponseEntity<Game> play(@RequestBody Attempt attempt) {
        Game current = service.play(attempt);

        return ResponseEntity.ok(current);
    }

}
