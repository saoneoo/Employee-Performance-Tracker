package sawan.dev.com.EmployeePerformanceTracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import sawan.dev.com.EmployeePerformanceTracker.entity.Enum.Role;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.repository.UserRepository;

@SpringBootApplication
@EnableScheduling   // ✅ Enable scheduling support
public class EmployeePerformanceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeePerformanceTrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByName("admin").isEmpty()) {
				User user = new User();
				user.setName("admin");
				user.setEmail("admin@gmail.com");
				user.setPassword(passwordEncoder.encode("admin123")); // ✅ encode password
				user.setRole(Role.ROLE_MANAGER);
				userRepository.save(user);
				System.out.println("✅ Admin user created in DB");
			}
		};
	}


}
