package sawan.dev.com.EmployeePerformanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.com.EmployeePerformanceTracker.entity.Performance;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    // Performance of one employee
    Optional<Performance>findByUser(User user);

//    // Performance records for a given month
   List<Performance> findByMonth(YearMonth month);
}