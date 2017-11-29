package com.dcanaktemur.controller;

import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.model.Error;
import com.dcanaktemur.model.Guess;
import com.dcanaktemur.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by dogus on 11/27/17.
 *
 * Main entry to the games
 * */

@RestController
public class GameController {

    @Autowired
    IGameService gameService;

    /**
     * Fetches all games
     *
     * This API call allows you to fetch all the games that have ever existed.
     */
    @RequestMapping(value = "/game", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Iterable<Game> listGames(){
        return gameService.listGames();
    }

    /**
     * Starts a new game.
     * Starts a new game for a player. Player object is used as a body to the request.
     */
    @RequestMapping(value = "/game", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    @ResponseBody
    public Game startGame(@RequestBody Player player){
       return gameService.startGame(player);
    }

    /**
     * Fetches game information
     *
     *   This API call allows you to get information about a single game. It returns the game object.
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity fetchGameInfo(@PathVariable Long gameId){
        Game game = gameService.fetchGameInfo(gameId);

        if(game != null){
            return ResponseEntity.ok(game);
        }else{
            Error error = new Error(0,"Game does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     *Guess a letter in the game.
     *
     * This API call allows you to guess a letter in ongoing game. If you guess incorrectly and your number of guesses is full the game ends.
     If you guess the letter correctly the game continues. Guessing of the whole word is not available in version 1.
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.PUT,produces = "application/json",consumes = "application/json")
    public ResponseEntity guess(@PathVariable Long gameId, @RequestBody Guess guess){
        try {
            Game game = gameService.guess(gameId,guess);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            Error error = new Error(0,e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Gives up.
     *
     * This API call allows you to give up on a game. It ends the game and returns the game object with word revealed.
     Game is marked as lost.
     * @param gameId
     * @return
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.DELETE,produces = "application/json")
    public ResponseEntity delete(@PathVariable Long gameId){
        try {
            Game game = gameService.delete(gameId);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            Error error = new Error(0,e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
