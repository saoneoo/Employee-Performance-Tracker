package sawan.dev.com.EmployeePerformanceTracker.service;

import sawan.dev.com.EmployeePerformanceTracker.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Long userId, Task task);

    Task getTaskById(Long taskId);

    List<Task> getALLTasks();

    Task updateTask(Long taskId, Task updatedTask);

    void deleteTask(Long taskId);

    Task markTaskCompleted(Long taskId);

    Task approvalTask(Long taskId,int reviewScore);

    Task rejectTask(Long taskId,String Description);
}
