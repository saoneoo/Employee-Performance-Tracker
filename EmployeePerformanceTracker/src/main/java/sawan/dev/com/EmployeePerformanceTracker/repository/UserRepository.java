package sawan.dev.com.EmployeePerformanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//
//    // For login / authentication
 Optional<User> findByName(String username);

//    // For checking role
//    boolean existsByUsername(String username);
}