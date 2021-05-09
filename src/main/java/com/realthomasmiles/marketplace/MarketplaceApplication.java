package com.realthomasmiles.marketplace;

import com.realthomasmiles.marketplace.model.marketplace.Category;
import com.realthomasmiles.marketplace.model.marketplace.Location;
import com.realthomasmiles.marketplace.model.marketplace.Offer;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.model.user.Role;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.model.user.UserRole;
import com.realthomasmiles.marketplace.repository.marketplace.CategoryRepository;
import com.realthomasmiles.marketplace.repository.marketplace.LocationRepository;
import com.realthomasmiles.marketplace.repository.marketplace.OfferRepository;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.repository.user.RoleRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
import com.realthomasmiles.marketplace.util.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class MarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}


	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
						   CategoryRepository categoryRepository, LocationRepository locationRepository,
						   PostingRepository postingRepository, OfferRepository offerRepository) {
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
						.setPhoneNumber("1234567")
						.setRoles(Collections.singletonList(userRole));
				userRepository.save(user);
			}

			Category computersCategory = categoryRepository.findByNameIgnoreCase("computers");
			if (computersCategory == null) {
				computersCategory = new Category()
						.setName("Computers");
				categoryRepository.save(computersCategory);
			}

			Location akademicheskayaLocation = locationRepository.findByNameIgnoreCase("akademicheskaya");
			if (akademicheskayaLocation == null) {
				akademicheskayaLocation = new Location()
						.setName("Akademicheskaya");
				locationRepository.save(akademicheskayaLocation);
			}

			List<Posting> adminLaptopPostings = postingRepository.findByNameContainsIgnoreCase("admin init laptop");
			Posting adminLaptopPosting;
			if (adminLaptopPostings.size() == 0) {
				adminLaptopPosting = new Posting()
						.setArticle("article")
						.setAuthor(admin)
						.setName("Admin INIT Laptop")
						.setCategory(computersCategory)
						.setLocation(akademicheskayaLocation)
						.setDescription("Admin laptop for sale")
						.setIsActive(true)
						.setPosted(DateUtils.today())
						.setPrice(30000L);
				postingRepository.save(adminLaptopPosting);
			} else {
				adminLaptopPosting = adminLaptopPostings.get(0);
			}

			List<Posting> userLaptopPostings = postingRepository.findByNameContainsIgnoreCase("user init laptop");
			Posting userLaptopPosting;
			if (userLaptopPostings.size() == 0) {
				userLaptopPosting = new Posting()
						.setArticle("article")
						.setAuthor(user)
						.setName("User INIT Laptop")
						.setCategory(computersCategory)
						.setLocation(akademicheskayaLocation)
						.setDescription("User laptop for sale")
						.setIsActive(true)
						.setPosted(DateUtils.today())
						.setPrice(25000L);
				userLaptopPosting = postingRepository.save(userLaptopPosting);
			} else {
				userLaptopPosting = userLaptopPostings.get(0);
			}

			List<Offer> userLaptopOffers = offerRepository.findByPostingIdAndAuthorId(userLaptopPosting.getId(), admin.getId());
			if (userLaptopOffers.size() == 0) {
				Offer userLaptopOffer = new Offer()
						.setAuthor(admin)
						.setPosting(userLaptopPosting)
						.setOffered(DateUtils.today())
						.setAmount(20000L);
				offerRepository.save(userLaptopOffer);
			}

			List<Offer> adminLaptopOffers = offerRepository.findByPostingIdAndAuthorId(adminLaptopPosting.getId(), user.getId());
			if (adminLaptopOffers.size() == 0) {
				Offer adminLaptopOffer = new Offer()
						.setAuthor(user)
						.setPosting(adminLaptopPosting)
						.setOffered(DateUtils.today())
						.setAmount(25000L);
				offerRepository.save(adminLaptopOffer);
			}
		};
	}

}
