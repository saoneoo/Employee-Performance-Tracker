package sawan.dev.com.EmployeePerformanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawan.dev.com.EmployeePerformanceTracker.entity.Reward;

import java.time.YearMonth;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    Optional<Reward> findByMonth(YearMonth month);
}
