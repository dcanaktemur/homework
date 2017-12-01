package com.dcanaktemur.service;

import com.dcanaktemur.db.GameRepository;
import com.dcanaktemur.db.WordRepository;
import com.dcanaktemur.db.model.Game;
import com.dcanaktemur.db.model.Player;
import com.dcanaktemur.db.model.Word;
import com.dcanaktemur.model.Guess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by dogus on 11/27/17.
 */

@Service
public class GameService implements IGameService{

    @Autowired
    IFileService fileService;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    WordRepository wordRepository;

    private ResourceLoader resourceLoader;

    @Autowired
    public GameService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Iterable<Game> listGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game startGame(Player player) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:words.txt");
        InputStream wordsAsFile = resource.getInputStream();
        String word = getRandomWord(fileService.readFile(wordsAsFile));


        Game game = new Game();
        game.setGuessesLeft(8);
        game.setGuessedWord(mask(word,"*"));
        game.setGameStatus("ongoing");
        game.setPlayer(player.getName());
        game = gameRepository.save(game);

        Word w = new Word();
        w.setGameId(game.getId());
        w.setWord(word);

        wordRepository.save(w);

        return game;
    }

    private String mask(String word, String s) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< word.length(); i++)
            sb.append("*");
        return sb.toString();
    }

    private String getRandomWord(List<String> words) {
        Random random = new Random();
        String randomWord = words.get(random.nextInt(words.size()));

        return randomWord;
    }

    @Override
    public Game fetchGameInfo(Long gameId) {
        return gameRepository.findOne(gameId);
    }

    @Override
    public Game delete(Long gameId) throws Exception {
        Game game = gameRepository.findOne(gameId);

        if(game == null)
            throw new Exception("Game was not found.");

        if(!game.getGameStatus().equals("ongoing"))
            throw new Exception("Game was not found.");

        game.setGameStatus("lost");

        return gameRepository.save(game);
    }

    @Override
    public Game guess(Long gameId, Guess guess) throws Exception {
        if(!guess.getLetter().matches("[a-zA-Z]"))
            throw new Exception("Guess was not made.");
        guess.setLetter(guess.getLetter().toLowerCase());
        Game game = gameRepository.findOne(gameId);
        if(game == null)
            throw new Exception("Guess was not made.");
        else{
            Word wordModel = wordRepository.findByGameId(game.getId());
            String word = wordModel.getWord();
            if(game.getGameStatus().equals("ongoing")){
                boolean isContain = containsLetter(guess.getLetter(),word);
                if(!isContain){
                    String incorrectLetters = game.getIncorrectLetters() == null ? "" : game.getIncorrectLetters();
                    incorrectLetters +=","+guess.getLetter();
                    if(game.getGuessesLeft()-1 < 1)
                        game.setGameStatus("lost");
                    game.setGuessesLeft(game.getGuessesLeft()-1);
                    game.setIncorrectLetters(incorrectLetters);
                }else{
                    String revealedWord = reveal(guess.getLetter(),word,game.getGuessedWord());
                    boolean isSolved = solve(revealedWord,word);
                    if(isSolved){
                        game.setGameStatus("won");
                    }
                    game.setGuessedWord(revealedWord);
                    game.setGuesses(game.getGuesses()+1);
                }
                return gameRepository.save(game);
            }else
                throw new Exception("Guess was not made.");
        }
    }

    private boolean solve(String guess, String word) {
        return word.equals(guess);
    }

    private String reveal(String letter, String word, String guessedWord) {
     StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i< word.length(); i++){
          char c = word.charAt(i) == letter.charAt(0) ? letter.charAt(0) : guessedWord.charAt(i);
          stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private boolean containsLetter(String letter, String word) {
        for(int i = 0; i< word.length(); i++){
            if(word.charAt(i) == letter.charAt(0))
                return true;
        }

        return false;
    }


}
