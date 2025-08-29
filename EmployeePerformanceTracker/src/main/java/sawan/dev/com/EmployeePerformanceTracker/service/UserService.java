package sawan.dev.com.EmployeePerformanceTracker.service;

import sawan.dev.com.EmployeePerformanceTracker.entity.Enum.Role;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //  1. Basic CRUD
    User createUser(User user);

     Optional<User>getUserById(Long id);

     List<User> getAllUsers();

     User updateUser (Long id,User updatedUser);

     void deleteUser(Long id);

     // 2. Role Management

    User assignRole(Long userId, Role role);

    //3.Task management
    Task assignTaskToUser(Long userId, Task task);
    List<Task>getUserTasks(Long userId);

    //4. performance

    int getUserPerformancePoints(Long userId, int month,int year);
}
