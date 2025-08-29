package sawan.dev.com.EmployeePerformanceTracker.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.com.EmployeePerformanceTracker.dto.TaskDto;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.service.TaskService;
import sawan.dev.com.EmployeePerformanceTracker.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    // ✅ Create Task
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        User user = userService.getUserById(taskDto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = modelMapper.map(taskDto, Task.class);
        Task savedTask = taskService.createTask(taskDto.getAssignedToId(), task);

        return ResponseEntity.ok(modelMapper.map(savedTask, TaskDto.class));
    }

    // ✅ Get All Tasks 
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getALLTasks()
                .stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // ✅ Get Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(modelMapper.map(task, TaskDto.class));
    }

    // ✅ Update Task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(id, modelMapper.map(taskDto, Task.class));
        return ResponseEntity.ok(modelMapper.map(updatedTask, TaskDto.class));
    }

    // ✅ Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Mark Task Completed
    @PutMapping("/{id}/complete")
    public ResponseEntity<TaskDto> markTaskCompleted(@PathVariable Long id) {
        Task completedTask = taskService.markTaskCompleted(id);
        return ResponseEntity.ok(modelMapper.map(completedTask, TaskDto.class));
    }

    // ✅ Approve Task (Manager gives score)
    @PutMapping("/{id}/approve")
    public ResponseEntity<TaskDto> approveTask(@PathVariable Long id, @RequestParam int score) {
        Task approvedTask = taskService.approvalTask(id, score);
        return ResponseEntity.ok(modelMapper.map(approvedTask, TaskDto.class));
    }

    // ✅ Reject Task
    @PutMapping("/{id}/reject")
    public ResponseEntity<TaskDto> rejectTask(@PathVariable Long id, @RequestParam String description) {
        Task rejectedTask = taskService.rejectTask(id, description);
        return ResponseEntity.ok(modelMapper.map(rejectedTask, TaskDto.class));
    }
}
