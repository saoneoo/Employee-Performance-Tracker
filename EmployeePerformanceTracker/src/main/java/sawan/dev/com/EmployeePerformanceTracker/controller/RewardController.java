package sawan.dev.com.EmployeePerformanceTracker.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.com.EmployeePerformanceTracker.dto.RewardDto;
import sawan.dev.com.EmployeePerformanceTracker.entity.Reward;
import sawan.dev.com.EmployeePerformanceTracker.service.RewardService;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reward")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;
    private final ModelMapper modelMapper;  // ✅ inject ModelMapper

    // Utility method to convert Reward → RewardDto
    private RewardDto convertToDto(Reward reward) {
        RewardDto dto = modelMapper.map(reward, RewardDto.class);
        dto.setMonth(reward.getMonth().toString()); // YearMonth → String
        dto.setWinnerId(reward.getWinner().getId());
        dto.setEmployeeName(reward.getWinner().getName());
        return dto;
    }

    // 🔹 Declare winner for this month
    @PostMapping("/declare")
    public ResponseEntity<RewardDto> declareWinner() {
        Reward reward = rewardService.autoDeclareEmployeeOfTheMonth();
        return ResponseEntity.ok(convertToDto(reward));
    }

    // 🔹 Get winner of a month
    @GetMapping("/{year}/{month}")
    public ResponseEntity<RewardDto> getWinner(@PathVariable int year, @PathVariable int month) {
        Reward reward = rewardService.getEmployeeOfTheMonth(YearMonth.of(year, month));
        return ResponseEntity.ok(convertToDto(reward));
    }
}
