package com.example.game_service.api.repository;

import com.example.game_service.api.commons.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository <Game,Long>{

}
