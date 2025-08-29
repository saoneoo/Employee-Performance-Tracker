package sawan.dev.com.EmployeePerformanceTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RewardDto {
        private Long id;
        private String month;
        private int score;
        private Long winnerId;
        private String employeeName;
    }



