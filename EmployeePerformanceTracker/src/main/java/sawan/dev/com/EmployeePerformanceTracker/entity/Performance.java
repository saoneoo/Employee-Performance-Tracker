package sawan.dev.com.EmployeePerformanceTracker.entity;

import jakarta.persistence.*;
import lombok.*;
import sawan.dev.com.EmployeePerformanceTracker.YearMonthAttributeConverter;

import java.time.YearMonth;

@Entity
@Table(name="performance")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Performance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  int tasksCompleted;

    private  int score;  // points system for ranking


    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth month; // e.g., 2025-08


    // Relationship: Many performance records belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
