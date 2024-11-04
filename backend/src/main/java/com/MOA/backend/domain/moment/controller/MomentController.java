package com.MOA.backend.domain.moment.controller;

import com.MOA.backend.domain.moment.dto.request.MomentCreateRequestDto;
import com.MOA.backend.domain.moment.dto.response.MomentCreateResponseDto;
import com.MOA.backend.domain.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moment")
public class MomentController {

    private final MomentService momentService;

    @PostMapping
    public ResponseEntity<MomentCreateResponseDto> createMoment(@RequestBody MomentCreateRequestDto momentCreateRequestDto) {
        MomentCreateResponseDto momentDto = momentService.createMoment(momentCreateRequestDto);
        return ResponseEntity.ok(momentDto);
    }

    @DeleteMapping("/{moment_id}")
    public ResponseEntity<?> deleteMoment(@PathVariable(name = "moment_id") String momentId) {
        momentService.deleteMoment(momentId);
        return ResponseEntity.ok().body(ObjectId.get().toHexString() + "번 순간이 삭제가 완료되었습니다.");
    }
}
