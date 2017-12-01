package com.dcanaktemur.service;

import com.dcanaktemur.db.model.Player;

/**
 * Created by dogus on 11/28/17.
 */
public interface IPlayerService {
    Iterable<Player> listPlayers();

    Player createPlayer(Player player) throws Exception;

    Player fetchPlayerInformation(Long playerId);
}
