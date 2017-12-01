package com.dcanaktemur;

import com.dcanaktemur.db.PlayerRepository;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.service.FileService;
import com.dcanaktemur.service.IFileService;
import com.dcanaktemur.service.IPlayerService;
import com.dcanaktemur.service.PlayerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


}
