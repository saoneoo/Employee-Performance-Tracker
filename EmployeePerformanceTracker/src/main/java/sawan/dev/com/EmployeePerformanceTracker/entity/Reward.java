package sawan.dev.com.EmployeePerformanceTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Entity
@Table(name = "monthly_winners")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String winnerName;

    private YearMonth month; // Stores year+month (e.g., 2025-08)

    // Relationship: One winner is always a User
    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = false)
    private User winner;

    private int score; // Winner's score for that month

}
