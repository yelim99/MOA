package com.MOA.backend.domain.moment.service;

import com.MOA.backend.domain.moment.dto.request.MomentCreateRequestDto;
import com.MOA.backend.domain.moment.dto.request.MomentUpdateRequestDto;
import com.MOA.backend.domain.moment.dto.response.MomentCreateResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentDetailResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentResponseDto;
import com.MOA.backend.domain.moment.dto.response.MomentUpdateResponseDto;
import com.MOA.backend.domain.moment.entity.Moment;
import com.MOA.backend.domain.moment.repository.MomentRepository;
import com.MOA.backend.domain.moment.util.PinCodeUtil;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.exception.ForbiddenAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomentService {

    private final MomentRepository momentRepository;
    private final UserService userService;
    private final PinCodeUtil pinCodeUtil;
    private final MomentRedisService momentRedisService;

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

    public void deleteMoment(String momentId) {
        // 1. Redis에서 유저가 참여한 관계 매핑 먼저 지우기
        momentRedisService.deleteMomentParticipation(momentId);

        // 2. MongoDB에서 해당 moment 삭제
        momentRepository.deleteById(momentId);
    }

    public MomentUpdateResponseDto updateMoment(String momentId, MomentUpdateRequestDto momentUpdateCreateDto) {
        Moment moment = momentRepository.findById(momentId).orElseThrow();
        moment.update(momentUpdateCreateDto);
        momentRepository.save(moment);

        return MomentUpdateResponseDto.builder()
                .momentId(moment.getId().toHexString())
                .message("순간 수정이 완료되었습니다.")
                .PIN(moment.getMomentPin())
                .build();
    }

    public List<MomentResponseDto> getAllMoments() {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}", loginUser);

        // 내가 참여한 방 목록 조회
        Set<String> myMomentsIds = momentRedisService.getMyMoments(loginUser.getUserId());
        log.info("myMomentsIds: {}", myMomentsIds);

        Set<ObjectId> objectIds = myMomentsIds.stream().map(ObjectId::new).collect(Collectors.toSet());

        return momentRepository.findAllByIdInOrderByCreatedAtAsc(objectIds)
                .stream()
//                .sorted(Comparator.comparing(Moment::getCreatedAt)) // 생성일자 기준으로 정렬
                .map(moment -> MomentResponseDto.builder()
                .momentId(moment.getId().toHexString())
                .momentTitle(moment.getMomentName())
                .momentDescription(moment.getMomentDescription())
                .momentOwner(moment.getMomentOwner())
                .createdAt(moment.getCreatedAt())
                .build()).toList();
    }

    // 순간 상세 조회 == 내가 이미 참여한 순간에 입장
    public MomentDetailResponseDto getMoment(String momentId) {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}", loginUser);

        Set<String> myMoments = momentRedisService.getMyMoments(loginUser.getUserId());
        if(!myMoments.contains(momentId)) {
            throw new ForbiddenAccessException("순간에 대해 유효하지 않은 접근입니다.");
        }

        // Moment 찾아오기
        Moment moment = momentRepository.findById(momentId).orElseThrow(NoSuchElementException::new);

        return MomentDetailResponseDto.builder()
                        .id(moment.getId().toHexString())
                        .groupId(moment.getGroupId())
                        .momentPin(moment.getMomentPin())
                        .userIds(moment.getUserIds())
                        .momentName(moment.getMomentName())
                        .momentDescription(moment.getMomentDescription())
                        .momentOwner(moment.getMomentOwner())
                        .uploadOption(moment.getUploadOption())
                        .build();
    }

    public MomentDetailResponseDto participate(String momentId, String pin) {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}, {}", loginUser.getUserId(), loginUser.getUserEmail());

        // 입력한 PIN번호가 순간의 PIN번호와 일치하는 지를 검증
        // 1. 해당 Moment 가져오기
        Moment moment = momentRepository.findById(momentId).orElseThrow(NoSuchElementException::new);

        // 2. 해당 Moment의 PIN이 입력 PIN이랑 일치하는 지를 검증
        if(!pin.equals(moment.getMomentPin())) {
            throw new ForbiddenAccessException("PIN번호가 일치하지 않습니다.");
        }

        // 3. Moment에도 유저 정보 추가 반영
        List<Long> addedList = moment.getUserIds();
        addedList.add(loginUser.getUserId());
        moment.update(addedList);
        momentRepository.save(moment);

        // Redis 관계 매핑 반영
        momentRedisService.participateMoment(loginUser.getUserId(), momentId);
        log.info("유저 {}가 성공적으로 {} 순간 입장에 완료하였습니다.", loginUser.getUserEmail(), momentId);

        return MomentDetailResponseDto.builder()
                .id(moment.getId().toHexString())
                .groupId(moment.getGroupId())
                .momentPin(moment.getMomentPin())
                .userIds(moment.getUserIds())
                .momentName(moment.getMomentName())
                .momentDescription(moment.getMomentDescription())
                .momentOwner(moment.getMomentOwner())
                .uploadOption(moment.getUploadOption())
                .build();
    }

    public void userExit(String momentId) {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}, {}", loginUser.getUserId(), loginUser.getUserEmail());

        // 1. 해당 Moment 가져오기
        Moment moment = momentRepository.findById(momentId).orElseThrow(NoSuchElementException::new);

        // 2. Redis에서 유저가 참여한 관계 매핑 먼저 지우기
        momentRedisService.exitMoment(loginUser.getUserId(), momentId);

        // 3. MongoDB에서 Moment 내 유저 아이디 변경을 위한 필터링
        log.info("originalList: {}", moment.getUserIds());
        List<Long> userIds = moment.getUserIds();
        List<Long> modifiedList = userIds.stream().filter(id -> !id.equals(loginUser.getUserId())).toList();
        log.info("modifiedList: {}", modifiedList);

        // 4. 유저가 퇴장한 수정사항 반영
        moment.update(modifiedList);
        momentRepository.save(moment);
    }

    public Moment getMomentEntity(String momentId) {
        return momentRepository.findById(momentId).orElseThrow(NoSuchElementException::new);
    }

    public List<String> getMomentIds(Long groupId) {
        List<Moment> moments = momentRepository.findAllByGroupId(groupId);

        if(moments.isEmpty()) {
            throw new NoSuchElementException("순간이 존재하지 않습니다.");
        }

        return moments.stream().map(moment -> moment.getId().toHexString()).toList();
    }
}
