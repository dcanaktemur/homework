package com.dcanaktemur;

import com.dcanaktemur.db.GameRepository;
import com.dcanaktemur.db.WordRepository;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.service.FileService;
import com.dcanaktemur.service.GameService;
import com.dcanaktemur.service.IFileService;
import com.dcanaktemur.service.IGameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
public class GameServiceTests {

    @TestConfiguration
    static class GameServiceTestContextConfiguration{
        private ResourceLoader resourceLoader;
        @Bean
        public IGameService gameService(){
            return new GameService(resourceLoader);
        }
    }


    @Autowired
    IGameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private WordRepository wordRepository;

    @MockBean
    private IFileService fileService;


    @Test
    public void shouldReturnGamesWhenListGames() throws Exception {
        Iterable<Game> list = new ArrayList<>();
        Mockito.when(gameRepository.findAll()).thenReturn(list);

        Iterable<Game> games = gameService.listGames();

        Assert.assertNotNull(games);
    }

}
