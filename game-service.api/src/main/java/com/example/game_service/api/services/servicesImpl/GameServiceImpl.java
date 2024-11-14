package com.example.game_service.api.services.servicesImpl;

import com.example.game_service.api.commons.entities.Game;
import com.example.game_service.api.commons.exceptions.GameException;
import com.example.game_service.api.repository.GameRepository;
import com.example.game_service.api.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
@Override
    public Game saveGame(Game gameRequest){
        return this.gameRepository.save(gameRequest);
    }

    @Override
    public Game getGameById(String id) {
        return this.gameRepository.findById(Long.valueOf(id))
                .orElseThrow(()-> new GameException(HttpStatus.NOT_FOUND ,"Game not found"));
    }


    @Override
    public Game updateGame(String id, Game gameRequest) {
        log.info("Updating game with id: {}", id);
        return gameRepository.findById(Long.valueOf(id))
                .map(game -> {
                    updateGameFields(game, gameRequest);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "Game not found with id: " + id));
    }

    @Override
    public void deleteGame(String id) {
        log.info("Deleting game with id: {}", id);
        gameRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "Game not found with id: " + id));
        gameRepository.deleteById(Long.valueOf(id));
    }

    private void updateGameFields(Game existingGame, Game gameRequest) {
        existingGame.setName(gameRequest.getName());
        // Update other fields as needed
    }
}
