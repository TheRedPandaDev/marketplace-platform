package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

    List<Offer> findByPostingId(Long postingId);

    List<Offer> findByAuthorId(Long authorId);

    List<Offer> findByPostingIdAndAuthorId(Long postingId, Long authorId);

}
