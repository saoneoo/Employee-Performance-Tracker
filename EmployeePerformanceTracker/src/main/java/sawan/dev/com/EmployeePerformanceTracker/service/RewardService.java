package sawan.dev.com.EmployeePerformanceTracker.service;

import sawan.dev.com.EmployeePerformanceTracker.entity.Reward;

import java.time.YearMonth;

public interface RewardService {
    Reward autoDeclareEmployeeOfTheMonth();

    Reward getEmployeeOfTheMonth(YearMonth of);

}
