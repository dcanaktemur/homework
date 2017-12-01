package com.dcanaktemur;

import com.dcanaktemur.controller.GameController;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.model.Guess;
import com.dcanaktemur.service.IGameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTests {

    @Autowired
    private MockMvc mvc ;

    @MockBean
    private IGameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnGamesListWhenListGames() throws Exception {
        Game game = new Game();
        game.setPlayer("test");

        Iterable<Game> games = Arrays.asList(game);
        given(gameService.listGames()).willReturn(games);

        mvc.perform(get("/game")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].player", is(game.getPlayer())));
    }


    @Test
    public void shouldReturnGameWhenFetchGame() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setPlayer("test");

        given(gameService.fetchGameInfo(1L)).willReturn(game);

        mvc.perform(get("/game/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player", is(game.getPlayer())));
    }


    @Test
    public void shouldReturnGameWhenDeleteGame() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setPlayer("test");
        game.setGameStatus("ongoing");

        given(gameService.delete(1L)).willReturn(game);

        mvc.perform(delete("/game/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player", is(game.getPlayer())));
    }

    @Test
    public void shouldReturnGameWhenCreateGame() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("test");

        Game game = new Game();
        game.setId(1L);
        game.setPlayer("test");

        given(gameService.startGame(any())).willReturn(game);

        mvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player", is(player.getName())));
    }


    @Test
    public void shouldReturnGameWhenGuess() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setPlayer("test");

        Guess guess = new Guess();
        guess.setLetter("a");

        given(gameService.guess(eq(1L),anyObject())).willReturn(game);

        mvc.perform(put("/game/1")
                .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(guess)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player", is(game.getPlayer())));
    }



}
