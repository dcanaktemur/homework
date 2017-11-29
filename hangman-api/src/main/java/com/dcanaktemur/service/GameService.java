package com.dcanaktemur.service;

import com.dcanaktemur.db.GameRepository;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.model.Guess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dogus on 11/27/17.
 */

@Component
public class GameService implements IGameService{

    @Autowired
    GameRepository gameRepository;

    @Override
    public Iterable<Game> listGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game startGame(Player player) {
        Game game = new Game();
        game.setGameStatus("ongoing");
        game.setPlayer(player.getName());
        return gameRepository.save(game);
    }

    @Override
    public Game fetchGameInfo(Long gameId) {
        return gameRepository.findOne(gameId);
    }

    @Override
    public Game delete(Long gameId) throws Exception {
        Game game = gameRepository.findOne(gameId);

        if(game == null)
            throw new Exception("Game was not found.");

        if(!game.getGameStatus().equals("ongoing"))
            throw new Exception("Game was not found.");

        game.setGameStatus("lost");

        return gameRepository.save(game);
    }

    @Override
    public Game guess(Long gameId, Guess guess) throws Exception {
        Game game = gameRepository.findOne(gameId);
        if(game == null)
            throw new Exception("Guess was not made.");
        else{
            if(game.getGameStatus().equals("ongoing")){
                // Eğer kelime nin içinde bu harf yoksa (kelime nerde belli değil ama) ve tahmin sıfıra düştü ise oyunu bitir.
                boolean wrong = false ;
                if(wrong){
                    if(game.getGuessesLeft()-1 < 0)
                        game.setGameStatus("lost");
                    game.setGuessesLeft(game.getGuessesLeft()-1);
                }else{
                    game.setGuesses(game.getGuesses()+1);
                }
                return gameRepository.save(game);
            }else
                throw new Exception("Guess was not made.");
        }
    }


}
