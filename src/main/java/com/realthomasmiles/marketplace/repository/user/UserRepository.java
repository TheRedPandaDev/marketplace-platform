package com.realthomasmiles.marketplace.repository.user;

import com.realthomasmiles.marketplace.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
