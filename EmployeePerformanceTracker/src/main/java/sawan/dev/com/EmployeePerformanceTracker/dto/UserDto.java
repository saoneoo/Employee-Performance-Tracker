package sawan.dev.com.EmployeePerformanceTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sawan.dev.com.EmployeePerformanceTracker.entity.Enum.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String password;
    private Role role; // "EMPLOYEE" or "MANAGER"

}
