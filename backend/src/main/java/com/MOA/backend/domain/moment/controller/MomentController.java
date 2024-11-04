package com.MOA.backend.domain.moment.controller;

import com.MOA.backend.domain.moment.dto.request.MomentCreateRequestDto;
import com.MOA.backend.domain.moment.dto.response.MomentCreateResponseDto;
import com.MOA.backend.domain.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moment")
public class MomentController {

    private final MomentService momentService;

    @PostMapping("")
    public ResponseEntity<MomentCreateResponseDto> createMoment(@RequestBody MomentCreateRequestDto momentCreateRequestDto) {
        MomentCreateResponseDto momentDto = momentService.createMoment(momentCreateRequestDto);
        return ResponseEntity.ok(momentDto);
    }
}
