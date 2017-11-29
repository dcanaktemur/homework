package com.dcanaktemur;

import com.dcanaktemur.db.PlayerRepository;
import com.dcanaktemur.db.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepositoryTests {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	public void testFindByName(){
		Player player = new Player();
		player.setName("test");
		player.setAge(18);
		entityManager.persist(player);

		Player findByName = playerRepository.findByName(player.getName());
		Assert.assertEquals(findByName.getName(),player.getName());
	}

}
