package com.realthomasmiles.marketplace;

import com.realthomasmiles.marketplace.model.user.Role;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.model.user.UserRole;
import com.realthomasmiles.marketplace.repository.user.RoleRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class MarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}


	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			Role adminRole = roleRepository.findByRole(UserRole.ADMIN);
			if (adminRole == null) {
				adminRole = new Role();
				adminRole.setRole(UserRole.ADMIN);
				roleRepository.save(adminRole);
			}

			Role userRole = roleRepository.findByRole(UserRole.USER);
			if (userRole == null) {
				userRole = new Role();
				userRole.setRole(UserRole.USER);
				roleRepository.save(userRole);
			}

			User admin = userRepository.findByEmail("admin@gmail.com");
			if (admin == null) {
				admin = new User()
						.setEmail("admin@gmail.com")
						.setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
						.setFirstName("Thomas")
						.setLastName("Miles")
						.setPhoneNumber("123456")
						.setRoles(Collections.singletonList(adminRole));
				userRepository.save(admin);
			}

			User user = userRepository.findByEmail("user@gmail.com");
			if (user == null) {
				user = new User()
						.setEmail("user@gmail.com")
						.setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
						.setFirstName("Dmitry")
						.setLastName("Milya")
						.setPhoneNumber("123456")
						.setRoles(Collections.singletonList(userRole));
				userRepository.save(user);
			}
		};
	}

}
