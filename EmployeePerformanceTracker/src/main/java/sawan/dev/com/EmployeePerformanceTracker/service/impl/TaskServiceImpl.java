package sawan.dev.com.EmployeePerformanceTracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.repository.TaskRepository;
import sawan.dev.com.EmployeePerformanceTracker.repository.UserRepository;
import sawan.dev.com.EmployeePerformanceTracker.service.PerformanceService;
import sawan.dev.com.EmployeePerformanceTracker.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl  implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository; // to assign tasks
    private final PerformanceService performanceService; // link points after approval


    @Override
    public Task createTask(Long userId, Task task) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        task.setAssignedTo(user);
        task.setCompleted(false);
        task.setManagerApproved(false);
       return taskRepository.save(task);

    }
    public Task getTaskById(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new IllegalArgumentException("task not found "));
        return task;
    }



    /**
     * Fetch all tasks.
     */
    @Override
    public List<Task> getALLTasks() {
        return taskRepository.findAll();
    }



    /**
     * Update a task (e.g., title, description, deadline).
     */
    @Override
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());

        return taskRepository.save(existingTask);
    }



    /**
     * Delete a task by its ID.
     */
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);

    }



    /**
     * Employee marks a task as completed (but points not added yet).
     */
    @Override
    public Task markTaskCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setCompletedDate(LocalDate.now());

        task.setCompleted(true);
        task.setCompletedDate(LocalDate.now());
        return  taskRepository.save(task);

    }



    /**
     * Manager approves task -> points will be awarded to user.
     */
    @Override
    public Task approvalTask(Long taskId,int reviewScore) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        task.setManagerApproved(true);
        task.setManagerReviewScore(reviewScore);
        performanceService.updatePerformanceForTask(task.getAssignedTo(),task);

        return taskRepository.save(task);
    }



    /**
     * Manager rejects task (no points given).
     */
    @Override
    public Task rejectTask(Long taskId,String description) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
               task.setManagerApproved(false);
               task.setDescription(description);
        return taskRepository.save(task);
    }
}
