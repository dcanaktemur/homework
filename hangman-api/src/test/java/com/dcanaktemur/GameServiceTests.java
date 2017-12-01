package com.dcanaktemur;

import com.dcanaktemur.db.GameRepository;
import com.dcanaktemur.db.WordRepository;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.db.model.Word;
import com.dcanaktemur.model.Guess;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
public class GameServiceTests {

    @TestConfiguration
    static class GameServiceTestContextConfiguration{
        @Autowired
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


    @Test
    public void shouldReturnGameWhenFetchGameInformationWithGivenGameId() throws Exception {
        Game game = new Game();
        game.setPlayer("test");
        game.setId(2L);

        Mockito.when(gameRepository.findOne(game.getId())).thenReturn(game);

        Game resultGame = gameService.fetchGameInfo(game.getId());

        Assert.assertNotNull(resultGame);

        Assert.assertEquals(game.getPlayer(),resultGame.getPlayer());
    }

    @Test
    public void shouldReturnCreatedGameWhenCreateGameWithGivenPlayer() throws IOException {
        Player player = new Player();
        player.setAge(18);
        player.setName("test");
        player.setId(2L);


        Game game = new Game();
        game.setId(1L);
        game.setPlayer(player.getName());

        List<String> words = new ArrayList<>();
        words.add("test");
        words.add("example");

        Word word = new Word();
        word.setWord("test");

        Mockito.when(gameRepository.save(any(Game.class))).thenReturn(game);

        Mockito.when(fileService.readFile(any())).thenReturn(words);

        Mockito.when(wordRepository.save(word)).thenReturn(word);

        Game resultGame = gameService.startGame(player);

        Assert.assertEquals(resultGame.getPlayer(),game.getPlayer());

    }

    @Test
    public void shouldReturnLostGameWhenDeleteGameWithGivenGameId() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setGameStatus("ongoing");

        Mockito.when(gameRepository.findOne(game.getId())).thenReturn(game);
        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Game resultGame = gameService.delete(1L);

        Assert.assertEquals("lost",resultGame.getGameStatus());

    }

    @Test
    public void shouldReturnGameWhenGuessWithGivenGameIdAndLetter() throws Exception {
        Game game = new Game();
        game.setId(1L);
        game.setGameStatus("ongoing");
        game.setGuessedWord("*******");
        game.setGuessesLeft(8);

        Guess guess = new Guess();
        guess.setLetter("a");

        Word word = new Word();
        word.setGameId(1L);
        word.setWord("example");

        Mockito.when(gameRepository.findOne(game.getId())).thenReturn(game);
        Mockito.when(wordRepository.findByGameId(game.getId())).thenReturn(word);
        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Game resultGame = gameService.guess(1L,guess);

        Assert.assertNotNull(resultGame);

        Assert.assertEquals("**a****",resultGame.getGuessedWord());
    }



}
