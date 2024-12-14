package com.example.game_service.api.services.servicesImpl;

import com.example.game_service.api.commons.constants.TopicConstants;
import com.example.game_service.api.commons.entities.Game;
import com.example.game_service.api.commons.exceptions.GameException;
import com.example.game_service.api.repository.GameRepository;
import com.example.game_service.api.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final StreamBridge streamBridge;

    public GameServiceImpl(GameRepository gameRepository, StreamBridge streamBridge) {
        this.gameRepository = gameRepository;
        this.streamBridge = streamBridge;
    }
@Override
    public Game saveGame(String userId,Game gameRequest){
        gameRequest.setUserId(Integer.parseInt(userId));
        return Optional.of(gameRequest)
                .map(gameRepository::save)
                .map(this::sendGameEvent)
                .orElseThrow(()-> new GameException(HttpStatus.BAD_REQUEST, "Error saving game"));
    }

    private Game sendGameEvent(Game game) {
        Optional.of(game)
                .map(givenGame -> this.streamBridge.send(TopicConstants.GAME_CREATED_TOPIC,game))
                .map(bool -> game);

        return game;
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
