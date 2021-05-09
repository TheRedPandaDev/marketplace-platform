package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByNameIgnoreCase(String name);

}
