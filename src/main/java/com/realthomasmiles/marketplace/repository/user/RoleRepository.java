package com.realthomasmiles.marketplace.repository.user;

import com.realthomasmiles.marketplace.model.user.Role;
import com.realthomasmiles.marketplace.model.user.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(UserRole role);

}
