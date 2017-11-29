package com.dcanaktemur.service;

import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.model.Guess;

/**
 * Created by dogus on 11/27/17.
 */
public interface IGameService {
    Iterable<Game> listGames();
    Game startGame(Player player);
    Game fetchGameInfo(Long gameId);
    Game delete(Long gameId) throws Exception;

    Game guess(Long gameId,Guess guess) throws Exception;
}
