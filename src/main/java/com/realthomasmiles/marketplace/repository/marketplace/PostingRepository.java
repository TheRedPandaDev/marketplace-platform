package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Posting;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostingRepository extends CrudRepository<Posting, Long> {

    List<Posting> findByNameContainsIgnoreCase(String text);

    List<Posting> findByAuthorId(Long userId);

    List<Posting> findByLocationId(Long locationId);

}
