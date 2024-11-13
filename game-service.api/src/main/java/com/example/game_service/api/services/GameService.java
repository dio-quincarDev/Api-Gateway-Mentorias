package com.example.game_service.api.services;

import com.example.game_service.api.entities.Game;
import com.example.game_service.api.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame(Game gameRequest){
        Game gameCreatedInDb = this.gameRepository.save(gameRequest);
        return gameCreatedInDb;
    }
}
