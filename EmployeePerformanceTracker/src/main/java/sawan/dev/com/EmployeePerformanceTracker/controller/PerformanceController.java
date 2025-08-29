package sawan.dev.com.EmployeePerformanceTracker.controller;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.com.EmployeePerformanceTracker.dto.PerformanceDto;
import sawan.dev.com.EmployeePerformanceTracker.entity.Performance;
import sawan.dev.com.EmployeePerformanceTracker.service.PerformanceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {


    private final PerformanceService performanceService;
    private final ModelMapper modelMapper;


    // ✅ Create Performance
    @PostMapping
    public ResponseEntity<PerformanceDto> createPerformance(@RequestBody PerformanceDto performanceDto) {
        Performance performance = modelMapper.map(performanceDto, Performance.class);
        Performance saved = performanceService.createPerformance(performance);
        return ResponseEntity.ok(modelMapper.map(saved, PerformanceDto.class));
    }

    // ✅ Get all performances
    @GetMapping
    public ResponseEntity<List<PerformanceDto>> getAllPerformances() {
        List<PerformanceDto> list = performanceService.getAllPerformances()
                .stream()
                .map(perf -> modelMapper.map(perf, PerformanceDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // ✅ Get performance by ID
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceDto> getPerformanceById(@PathVariable Long id) {
        Performance perf = performanceService.getPerformanceById(id);
        return ResponseEntity.ok(modelMapper.map(perf, PerformanceDto.class));
    }

    // ✅ Update performance
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceDto> updatePerformance(@PathVariable Long id, @RequestBody PerformanceDto dto) {
        Performance updated = performanceService.updatePerformance(id, modelMapper.map(dto, Performance.class));
        return ResponseEntity.ok(modelMapper.map(updated, PerformanceDto.class));
    }

    // ✅ Delete performance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Custom API → Leaderboard
    @GetMapping("/leaderboard")
    public ResponseEntity<List<PerformanceDto>> getLeaderboard() {
        List<PerformanceDto> leaderboard = performanceService.getLeaderboard()
                .stream()
                .map(perf -> modelMapper.map(perf, PerformanceDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaderboard);
    }

    // ✅ Custom API → Get performance of a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<PerformanceDto> getUserPerformance(@PathVariable Long userId) {
        return performanceService.getUserPerformance(userId)
                .map(perf -> ResponseEntity.ok(modelMapper.map(perf, PerformanceDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }
}
