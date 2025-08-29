package sawan.dev.com.EmployeePerformanceTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate deadline;

    private boolean completed =false;

    // Relation : many task can belong to one employee

    @ManyToOne
    @JoinColumn(name="assign_to_id",nullable = false)
    private User assignedTo;

   // ✅ Track manager approval status
    private boolean managerApproved = false;

    private LocalDate completedDate;
    private int managerReviewScore;  // 0–5



}

