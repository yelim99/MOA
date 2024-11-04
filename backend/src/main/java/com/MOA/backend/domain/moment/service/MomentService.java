package com.MOA.backend.domain.moment.service;

import com.MOA.backend.domain.moment.dto.request.MomentCreateRequestDto;
import com.MOA.backend.domain.moment.dto.response.MomentCreateResponseDto;
import com.MOA.backend.domain.moment.entity.Moment;
import com.MOA.backend.domain.moment.repository.MomentRepository;
import com.MOA.backend.domain.moment.util.PinCodeUtil;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomentService {

    private final MomentRepository momentRepository;
    private final UserService userService;
    private final PinCodeUtil pinCodeUtil;

    @Transactional
    public MomentCreateResponseDto createMoment(MomentCreateRequestDto momentCreateRequestDto) {

        // TODO: : 리팩토링 필요 : 로그인유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}", loginUser);

        // TODO: : 리팩토링 필요 : 내가 속한 그룹들의 ID만 가져오는 로직

        // TODO: : 리팩토링 필요 : GroupId 선택한 뒤, 순간을 만드는 로직 (현재 임시방은 602로 임의 설정)
        Moment moment = Moment.builder()
                .groupId(momentCreateRequestDto.getGroupId() == null ? 602 : momentCreateRequestDto.getGroupId())
                .momentPin(pinCodeUtil.generatePinCode())
                .userIds(Stream.of(loginUser.getUserId()).collect(Collectors.toList()))
                .momentName(momentCreateRequestDto.getMomentName())
                .momentDescription(momentCreateRequestDto.getMomentDescription())
                .momentOwner(loginUser.getUserEmail())
                .uploadOption(momentCreateRequestDto.getUploadOption())
                .build();

        momentRepository.save(moment);
        log.info("Moment: {}", moment);
        String hexId = moment.getId().toHexString();

        return MomentCreateResponseDto.builder()
                .momentId(hexId)
                .message("순간 생성에 성공하였습니다.")
                .PIN(moment.getMomentPin())
                .build();
    }

    @Transactional
    public void deleteMoment(String momentId) {
        momentRepository.deleteById(momentId);
    }
}
