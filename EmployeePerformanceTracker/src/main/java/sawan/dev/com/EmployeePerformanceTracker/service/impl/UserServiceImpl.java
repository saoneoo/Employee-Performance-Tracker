package sawan.dev.com.EmployeePerformanceTracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sawan.dev.com.EmployeePerformanceTracker.entity.Enum.Role;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.repository.TaskRepository;
import sawan.dev.com.EmployeePerformanceTracker.repository.UserRepository;
import sawan.dev.com.EmployeePerformanceTracker.service.PerformanceService;
import sawan.dev.com.EmployeePerformanceTracker.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

   @Autowired
   private PerformanceService performanceService;


     // 1.basic CRUD
    @Override
    public User createUser(User user) {
        return  userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return  userRepository.findById(id).
                map(existing->{
                    existing.setName(updatedUser.getName());
                    existing.setEmail(updatedUser.getEmail());
                    existing.setRole(updatedUser.getRole());
                    return  userRepository.save(existing);
                }).
                orElseThrow(()->new RuntimeException("User not  found"));
    }

    @Override
    public void deleteUser(Long id) {
       userRepository.deleteById(id);
    }


    // 2. Role management
    @Override
    public User assignRole(Long userId, Role role) {
        User user = userRepository.findById(userId). orElseThrow(()->new RuntimeException("User not  found"));
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public Task assignTaskToUser(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        task.setAssignedTo(user); // link user to task
        Task savedTask = taskRepository.save(task);

        // ✅ Delegate performance update to PerformanceService
        performanceService.updatePerformanceForTask(user, task);

        return savedTask;

    }


        @Override
    public List<Task> getUserTasks(Long userId) {
        User user = userRepository.findById(userId). orElseThrow(()->new RuntimeException("User not  found"));
        return user.getTasks();
    }

    // 4. Performance
    @Override
    public int getUserPerformancePoints(Long userId, int month, int year) {
        return 0;
    }
}
