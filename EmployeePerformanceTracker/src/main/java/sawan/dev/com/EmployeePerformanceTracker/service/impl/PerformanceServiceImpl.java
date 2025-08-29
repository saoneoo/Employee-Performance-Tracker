package sawan.dev.com.EmployeePerformanceTracker.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.com.EmployeePerformanceTracker.entity.Performance;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.repository.PerformanceRepository;
import sawan.dev.com.EmployeePerformanceTracker.service.PerformanceService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    // 🔹 Standard CRUD
    @Override
    public Performance createPerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    public Performance getPerformanceById(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance not found"));
    }

    @Override
    public Performance updatePerformance(Long id, Performance performance) {
        Performance existing = getPerformanceById(id);
        existing.setTasksCompleted(performance.getTasksCompleted());
        existing.setScore(performance.getScore());
        existing.setUser(performance.getUser());
        return performanceRepository.save(existing);
    }

    @Override
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    // 🔹 Your custom methods
    @Override
    public void updatePerformanceForTask(User user, Task task) {
        Performance performance = performanceRepository.findByUser(user)
                .orElseGet(() -> Performance.builder()
                        .user(user)
                        .tasksCompleted(0)
                        .score(0)
                        .build());

        performance.setTasksCompleted(performance.getTasksCompleted() + 1);
        int score = calculateScore(task, task.getCompletedDate(), task.getManagerReviewScore());
        performance.setScore(performance.getScore() + score);

        performanceRepository.save(performance);
    }

    @Override
    public int calculateScore(Task task, LocalDate completedDate, int managerReviewScore) {
        int reviewPoints = Math.max(0, Math.min(managerReviewScore, 5));
        int completionPoints = 0;

        if (task.isManagerApproved()) {
            completionPoints = 5;
            long daysLate = ChronoUnit.DAYS.between(task.getDeadline(), completedDate);
            if (daysLate > 0) {
                double penalty = daysLate * 0.5;
                completionPoints = (int) Math.max(0, completionPoints - penalty);
            }
        }

        int bonusPoints = completedDate.isBefore(task.getDeadline()) ? 2 : 0;
        return completionPoints + bonusPoints + reviewPoints;
    }

    @Override
    public List<Performance> getLeaderboard() {
        return performanceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Performance::getScore).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Performance> getUserPerformance(Long userId) {
        return performanceRepository.findById(userId);
    }
}
