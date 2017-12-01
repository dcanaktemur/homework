package com.dcanaktemur;

import com.dcanaktemur.controller.GameController;
import com.dcanaktemur.controller.PlayerController;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.service.IGameService;
import com.dcanaktemur.service.IPlayerService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
