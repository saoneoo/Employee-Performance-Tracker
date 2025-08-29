package sawan.dev.com.EmployeePerformanceTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private LocalDate completedDate;
    private boolean managerApproved;
    private int managerReviewScore;
    private Long assignedToId;  // instead of embedding User entity
}
