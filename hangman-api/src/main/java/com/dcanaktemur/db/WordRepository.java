package com.dcanaktemur.db;

import com.dcanaktemur.db.model.Word;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dogus on 12/1/17.
 */
public interface WordRepository extends CrudRepository<Word,Long> {

    Word findByGameId(Long gameId);
}
