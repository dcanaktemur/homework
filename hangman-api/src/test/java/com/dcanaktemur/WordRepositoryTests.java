package com.dcanaktemur;

import com.dcanaktemur.db.WordRepository;
import com.dcanaktemur.db.model.Word;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WordRepositoryTests {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private WordRepository wordRepository;

	@Test
	public void testFindByGameId(){
		Word word = new Word();
		word.setWord("test");
		word.setGameId(1L);
		entityManager.persist(word);

		Word findByGameId = wordRepository.findByGameId(1L);
		Assert.assertEquals(findByGameId.getWord(),word.getWord());
	}

}
