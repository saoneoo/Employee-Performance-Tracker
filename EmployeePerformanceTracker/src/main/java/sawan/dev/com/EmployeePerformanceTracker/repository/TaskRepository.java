package sawan.dev.com.EmployeePerformanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sawan.dev.com.EmployeePerformanceTracker.entity.Task;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
//    // All tasks of a particular employee
//    List<Task> findByAssignedToId(Long employeeId);
//
//    // All tasks assigned by a manager
//    List<Task> findByAssignedById(Long managerId);

}