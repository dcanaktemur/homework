package com.dcanaktemur.db;

import com.dcanaktemur.db.model.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dogus on 11/27/17.
 */
public interface PlayerRepository extends CrudRepository<Player,Long> {
    Player findByName(String name);
}
