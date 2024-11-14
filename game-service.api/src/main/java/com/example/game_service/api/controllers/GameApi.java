package com.example.game_service.api.controllers;

import com.example.game_service.api.commons.constants.ApiPathVariables;
import com.example.game_service.api.commons.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.GAME_ROUTE)
public interface GameApi {
    @PostMapping
    ResponseEntity<Game>saveGame(@RequestBody Game game);

    @RequestMapping("/{id}")
    ResponseEntity<Game>getGameById(@PathVariable String id);
}
