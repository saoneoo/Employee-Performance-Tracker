package sawan.dev.com.EmployeePerformanceTracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDto {
    private String userName;
    private int tasksCompleted;
    private int score;
    private YearMonth month;
}
