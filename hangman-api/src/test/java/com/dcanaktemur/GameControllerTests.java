package com.dcanaktemur;

import com.dcanaktemur.controller.GameController;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.service.IGameService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
