package sawan.dev.com.EmployeePerformanceTracker.service;

import org.springframework.security.core.parameters.P;
import sawan.dev.com.EmployeePerformanceTracker.entity.Performance;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface PerformanceService {
    Performance createPerformance(Performance performance);
    List<Performance> getAllPerformances();
    Performance getPerformanceById(Long id);
    Performance updatePerformance(Long id, Performance performance);
    void deletePerformance(Long id);

    // ✅ Domain-specific methods
    void updatePerformanceForTask(User user, Task task);
    int calculateScore(Task task, LocalDate completedDate, int managerReviewScore);
    List<Performance> getLeaderboard();
    Optional<Performance> getUserPerformance(Long userId);
}
