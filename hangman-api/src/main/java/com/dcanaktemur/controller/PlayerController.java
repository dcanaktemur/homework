package com.dcanaktemur.controller;

import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.model.Error;
import com.dcanaktemur.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dogus on 11/28/17.
 */
@RestController
public class PlayerController {

    @Autowired
    IPlayerService playerService;

    /**
     * List players.
     *  This API call allows to list all players that have registered.
     *
     * @return
     */
    @RequestMapping(value = "/player",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Iterable<Player> listPlayers(){
        return playerService.listGames();
    }


    /**
     * Create player
     *
     *  This API call allows to create a new player. If the player name is already taken 400 error is thrown.
     *
     * @param player
     * @return
     */
    @RequestMapping(value = "/player",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    public ResponseEntity createPlayer(@RequestBody Player player){
        try {
            Player p = playerService.createPlayer(player);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Fetch player information.
     *
     * This API call allows you to fetch the player information object.
     *
     * @param playerId
     * @return
     */
    @RequestMapping(value = "/player/{playerId}",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity fetchPlayerInformation(@PathVariable Long playerId){
        Player player = playerService.fetchPlayerInformation(playerId);
        if(player != null)
            return ResponseEntity.ok(player);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(0,"Player was not found"));
    }

}
