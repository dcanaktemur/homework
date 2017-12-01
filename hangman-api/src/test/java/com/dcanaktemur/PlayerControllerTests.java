package com.dcanaktemur;

import com.dcanaktemur.controller.GameController;
import com.dcanaktemur.controller.PlayerController;
import com.dcanaktemur.db.PlayerRepository;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.service.IGameService;
import com.dcanaktemur.service.IPlayerService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

    @Autowired
    private MockMvc mvc ;

    @MockBean
    private IPlayerService playerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnPlayersListWhenListPlayers() throws Exception {
        Player player = new Player();
        player.setName("test");

        Iterable<Player> players = Arrays.asList(player);
        given(playerService.listPlayers()).willReturn(players);

        mvc.perform(get("/player")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(player.getName())));
    }

    @Test
    public void shouldReturnPlayerWhenFetchPlayerInfo() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("test");

        given(playerService.fetchPlayerInformation(1L)).willReturn(player);

        mvc.perform(get("/player/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(player.getName())));
    }

    @Test
    public void shouldReturnPlayerWhenCreatePlayer() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("test");

        given(playerService.createPlayer(any())).willReturn(player);

        mvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(player.getName())));
    }



}
