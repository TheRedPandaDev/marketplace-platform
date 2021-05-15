package com.realthomasmiles.marketplace;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
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
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"),
				"api_key", System.getenv("CLOUDINARY_API_KEY"),
				"api_secret", System.getenv("CLOUDINARY_API_SECRET")));
		SingletonManager singletonManager = new SingletonManager();
		singletonManager.setCloudinary(cloudinary);
		singletonManager.init();

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

			Category electronicsCategory = categoryRepository.findByNameIgnoreCase("electronics");
			if (electronicsCategory == null) {
				electronicsCategory = new Category()
						.setName("Electronics");
				categoryRepository.save(electronicsCategory);
			}

			Category servicesCategory = categoryRepository.findByNameIgnoreCase("services");
			if (servicesCategory == null) {
				servicesCategory = new Category()
						.setName("Services");
				categoryRepository.save(servicesCategory);
			}

			Category transportCategory = categoryRepository.findByNameIgnoreCase("transport");
			if (transportCategory == null) {
				transportCategory = new Category()
						.setName("Transport");
				categoryRepository.save(transportCategory);
			}

			Category gamesCategory = categoryRepository.findByNameIgnoreCase("games");
			if (gamesCategory == null) {
				gamesCategory = new Category()
						.setName("Games");
				categoryRepository.save(gamesCategory);
			}

			Location akademicheskayaLocation = locationRepository.findByNameIgnoreCase("akademicheskaya");
			if (akademicheskayaLocation == null) {
				akademicheskayaLocation = new Location()
						.setName("Akademicheskaya");
				locationRepository.save(akademicheskayaLocation);
			}

			Location devyatkinoLocation = locationRepository.findByNameIgnoreCase("devyatkino");
			if (devyatkinoLocation == null) {
				devyatkinoLocation = new Location()
						.setName("Devyatkino");
				locationRepository.save(devyatkinoLocation);
			}

			Location politekhnicheskayaLocation = locationRepository.findByNameIgnoreCase("politekhnicheskaya");
			if (politekhnicheskayaLocation == null) {
				politekhnicheskayaLocation = new Location()
						.setName("Politekhnicheskaya");
				locationRepository.save(politekhnicheskayaLocation);
			}

			List<Posting> adminLaptopPostings = postingRepository.findByNameContainsIgnoreCase("admin init laptop");
			Posting adminLaptopPosting;
			if (adminLaptopPostings.size() == 0) {
				adminLaptopPosting = new Posting()
						.setArticle("article")
						.setPhoto("https://res.cloudinary.com/du4nommyx/image/upload/v1621058987/f5uwvvqy9yg31_m2tye4.jpg")
						.setAuthor(admin)
						.setName("Admin INIT Laptop")
						.setCategory(computersCategory)
						.setLocation(politekhnicheskayaLocation)
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
						.setPhoto("https://res.cloudinary.com/du4nommyx/image/upload/v1621057918/fhza3dgzhng8dq2gsgzl.jpg")
						.setCategory(computersCategory)
						.setLocation(politekhnicheskayaLocation)
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
