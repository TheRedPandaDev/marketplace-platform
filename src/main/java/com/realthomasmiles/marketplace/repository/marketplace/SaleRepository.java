package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

    Sale findByPostingId(Long postingId);

    List<Sale> findByBuyerId(Long buyerId);

    List<Sale> findBySellerId(Long sellerId);

}
