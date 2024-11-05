package com.MOA.backend.domain.moment.controller;

import com.MOA.backend.domain.moment.dto.request.MomentCreateRequestDto;
import com.MOA.backend.domain.moment.dto.request.MomentUpdateRequestDto;
import com.MOA.backend.domain.moment.dto.response.MomentCreateResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentDetailResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentUpdateResponseDto;
import com.MOA.backend.domain.moment.service.MomentRedisService;
import com.MOA.backend.domain.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moment")
public class MomentController {

    private final MomentService momentService;

    // 순간 생성
    @PostMapping
    public ResponseEntity<MomentCreateResponseDto> createMoment(@RequestBody MomentCreateRequestDto momentCreateRequestDto) {
        MomentCreateResponseDto momentDto = momentService.createMoment(momentCreateRequestDto);
        return ResponseEntity.ok(momentDto);
    }

    // 순간 삭제
    @DeleteMapping("/{moment_id}")
    public ResponseEntity<?> deleteMoment(@PathVariable(name = "moment_id") String momentId) {
        momentService.deleteMoment(momentId);
        return ResponseEntity.ok().body(ObjectId.get().toHexString() + "번 순간이 삭제가 완료되었습니다.");
    }

    // 순간 수정(제목, 설명)
    @PatchMapping("/{moment_id}")
    public ResponseEntity<MomentUpdateResponseDto> updateMoment(@PathVariable(name = "moment_id") String momentId,
                                                                @RequestBody MomentUpdateRequestDto momentUpdateRequestDto) {
        MomentUpdateResponseDto momentUpdateResponseDto = momentService.updateMoment(momentId, momentUpdateRequestDto);
        return ResponseEntity.ok(momentUpdateResponseDto);
    }

    // 순간 목록 조회
    @GetMapping()
    public ResponseEntity<List<MomentResponseDto>> getAllMoments() {
        List<MomentResponseDto> myMoments = momentService.getAllMoments();
        return ResponseEntity.ok(myMoments);
    }

    // 순간 상세 조회
    @GetMapping("/{moment_id}")
    public ResponseEntity<MomentDetailResponseDto> getMoment(@PathVariable(name = "moment_id") String momentId) {
        MomentDetailResponseDto moment = momentService.getMoment(momentId);
        return ResponseEntity.ok(moment);
    }

    // 순간 참여
    @PostMapping("/{moment_id}")
    public ResponseEntity<?> participate(@PathVariable(name = "moment_id") String momentId,
                                         @RequestParam(name = "PIN") String pin) {
        MomentDetailResponseDto moment = momentService.participate(momentId, pin);
        return ResponseEntity.ok().body(moment);
    }

    // 순간 퇴장
    @PutMapping("/{moment_id}")
    public ResponseEntity<?> exit(@PathVariable(name = "moment_id") String momentId) {
        momentService.userExit(momentId);
        return ResponseEntity.ok().body("성공적으로 퇴장되었습니다.");
    }
}
