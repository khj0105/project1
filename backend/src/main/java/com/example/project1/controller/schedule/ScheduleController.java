package com.example.project1.controller.schedule;

import com.example.project1.dto.schedule.response.ScheduleResponseDto;
import com.example.project1.provider.JwtProvider;
import com.example.project1.service.impl.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final JwtProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getMySchedule(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        List<ScheduleResponseDto> schedule = scheduleService.getSchedule(email);
        return ResponseEntity.ok(schedule);
    }
}
