package com.dcanaktemur.service;

import com.dcanaktemur.db.PlayerRepository;
import com.dcanaktemur.db.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by dogus on 11/28/17.
 */
@Service
public class PlayerService implements IPlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Iterable<Player> listPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player createPlayer(Player player) throws Exception {
        Player existPlayer = playerRepository.findByName(player.getName());

        if(existPlayer != null){
            throw new Exception("Player already exists");
        }
        return playerRepository.save(player);
    }

    @Override
    public Player fetchPlayerInformation(Long playerId) {
        return playerRepository.findOne(playerId);
    }
}
