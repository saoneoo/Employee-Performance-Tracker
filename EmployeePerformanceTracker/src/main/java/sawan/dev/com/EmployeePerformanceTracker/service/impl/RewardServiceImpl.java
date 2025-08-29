package sawan.dev.com.EmployeePerformanceTracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sawan.dev.com.EmployeePerformanceTracker.entity.Reward;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.repository.RewardRepository;
import sawan.dev.com.EmployeePerformanceTracker.repository.UserRepository;
import sawan.dev.com.EmployeePerformanceTracker.service.RewardService;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;

    /**
     * ✅ Automatically declare winner on 1st day of every month at 00:01
     * CRON: second minute hour day month weekday
     * "0 1 0 1 * *" = at 00:01 AM on 1st of every month
     */
    @Scheduled(cron = "0 1 0 1 * *")  // Runs every 1st day of month at 00:01 AM
    @Transactional
    public Reward autoDeclareEmployeeOfTheMonth() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);

        // 🔒 Check if already declared
        if (rewardRepository.findByMonth(lastMonth).isPresent()) {
            System.out.println("⚠️ Winner for " + lastMonth + " already declared!");
            return null;  // nothing new declared
        }

        List<User> employees = userRepository.findAll();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees found for " + lastMonth);
            return null;
        }

        // ⚡ Find highest scorer
        User winner = employees.stream()
                .max(Comparator.comparingInt(User::getTotalPerformanceScore))
                .orElseThrow(() -> new RuntimeException("No performance data"));

        // Save reward
        Reward reward = new Reward();
        reward.setMonth(lastMonth);
        reward.setWinner(winner);
        reward.setScore(winner.getTotalPerformanceScore());
        reward.setWinnerName(winner.getName());

        Reward savedReward = rewardRepository.save(reward);

        System.out.println("🎉 Employee of the Month for " + lastMonth + " is: " + winner.getName());
        return savedReward;
    }


    public Reward getEmployeeOfTheMonth(YearMonth month) {
        return rewardRepository.findByMonth(month)
                .orElseThrow(() -> new RuntimeException("No reward declared for " + month));
    }
}
