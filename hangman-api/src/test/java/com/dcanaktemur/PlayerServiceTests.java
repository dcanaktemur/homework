package com.dcanaktemur;

import com.dcanaktemur.db.PlayerRepository;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.service.IPlayerService;
import com.dcanaktemur.service.PlayerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
public class PlayerServiceTests {

    @TestConfiguration
    static class PlayerServiceTestContextConfiguration{
        @Bean
        public IPlayerService playerService(){
            return new PlayerService();
        }
    }

    @Autowired
    IPlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void shouldReturnPlayersWhenListPlayers() throws Exception {
        Iterable<Player> list = new ArrayList<>();
        Mockito.when(playerRepository.findAll()).thenReturn(list);

        Iterable<Player> players = playerService.listPlayers();

        Assert.assertNotNull(players);
    }

    @Test
    public void shouldReturnPlayerWhenCreatePlayerWithGivenPlayer() throws Exception {
        Player player = new Player();
        player.setAge(18);
        player.setName("test");

        Mockito.when(playerRepository.save(player)).thenReturn(player);

        Player savedPlayer = playerService.createPlayer(player);

        Assert.assertEquals(player.getName(),savedPlayer.getName());

    }

    @Test
    public void shouldReturnPlayerWhenFetchPlayerInformationWithGivenPlayerId() throws Exception {
        Player player = new Player();
        player.setAge(18);
        player.setName("test");
        player.setId(2L);

        Mockito.when(playerRepository.findOne(player.getId())).thenReturn(player);

        Player resultPlayer = playerService.fetchPlayerInformation(player.getId());

        Assert.assertNotNull(resultPlayer);

        Assert.assertEquals(player.getName(),resultPlayer.getName());
    }

}
