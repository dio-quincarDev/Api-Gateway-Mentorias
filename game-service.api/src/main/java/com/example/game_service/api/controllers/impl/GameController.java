package com.example.game_service.api.controllers.impl;

import com.example.game_service.api.commons.entities.Game;
import com.example.game_service.api.controllers.GameApi;
import com.example.game_service.api.services.servicesImpl.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GameController implements GameApi {
    private final GameServiceImpl gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }


    @Override
    public ResponseEntity<Game> saveGame(@RequestHeader("userIdRequest") String userId, @RequestBody Game game) {
        Game gameCreated = this.gameService.saveGame(userId,game);
        return ResponseEntity.ok(gameCreated);
    }

    @Override
    public ResponseEntity<Game> getGameById(String id) {
        return ResponseEntity.ok(this.gameService.getGameById(id));
    }

    @Override
    public ResponseEntity<Game> updateGame(String id, Game game) {
        Game updatedGame = this.gameService.updateGame(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    @Override
    public ResponseEntity<Void> deleteGame(String id) {
        this.gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}


