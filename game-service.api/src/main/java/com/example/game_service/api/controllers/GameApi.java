package com.example.game_service.api.controllers;

import com.example.game_service.api.commons.constants.ApiPathVariables;
import com.example.game_service.api.commons.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.GAME_ROUTE)
public interface GameApi {
    @PostMapping
    ResponseEntity<Game>saveGame(@RequestBody Game game);

    @RequestMapping("/{id}")
    ResponseEntity<Game>getGameById(@PathVariable String id);


    @PutMapping("/{id}")
    ResponseEntity<Game> updateGame(@PathVariable String id, @RequestBody Game game);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGame(@PathVariable String id);
}
