package sawan.dev.com.EmployeePerformanceTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.com.EmployeePerformanceTracker.entity.Enum.Role;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private  String  password;

    @Column(nullable = false,unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    // Relationships
    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Task> tasks; // Tasks assigned to this user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Performance> performances;

    @OneToMany(mappedBy = "winner")
    @JsonIgnore   // 👈 prevent infinite recursion
    private List<Reward> rewards;


    public int getTotalPerformanceScore() {
        if (performances == null) return 0;
        return performances.stream()
                .mapToInt(Performance::getScore)
                .sum();
    }







}
