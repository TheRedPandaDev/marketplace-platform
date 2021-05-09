package com.realthomasmiles.marketplace.repository.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByNameIgnoreCase(String name);

}
