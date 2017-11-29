package com.dcanaktemur.db;

import com.dcanaktemur.db.model.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dogus on 11/27/17.
 */
public interface GameRepository extends CrudRepository<Game,Long> {
}
