package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Posting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends CrudRepository<Posting, Long> {

    List<Posting> findByNameContainsIgnoreCase(String text);

    List<Posting> findByAuthorId(Long userId);

    List<Posting> findByLocationId(Long locationId);

    List<Posting> findByCategoryId(Long categoryId);

}
