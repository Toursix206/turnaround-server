package com.toursix.turnaround.service.user;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_NICKNAME_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.Point;
import com.toursix.turnaround.domain.user.Setting;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.OnbordingRepository;
import com.toursix.turnaround.domain.user.repository.PointRepository;
import com.toursix.turnaround.domain.user.repository.SettingRepository;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.user.dto.request.CreateUserRequestDto;
import com.toursix.turnaround.service.user.dto.request.NicknameValidateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final PointRepository pointRepository;
    private final OnbordingRepository onbordingRepository;

    private final JwtUtils jwtProvider;

    public Long registerUser(CreateUserRequestDto request) {
        UserServiceUtils.validateNotExistsUser(userRepository, request.getSocialId(),
                request.getSocialType());
        User user = userRepository.save(
                User.newInstance(request.getSocialId(), request.getSocialType(),
                        pointRepository.save(Point.newInstance()),
                        settingRepository.save(Setting.newInstance())));
        Onboarding onboarding = onbordingRepository.save(
                Onboarding.newInstance(user, request.getProfileType(), request.getNickname()));
        User conflictFcmTokenUser = userRepository.findUserByFcmToken(request.getFcmToken());
        if (conflictFcmTokenUser != null) {
            jwtProvider.expireRefreshToken(conflictFcmTokenUser.getId());
            conflictFcmTokenUser.resetFcmToken();
        }
        user.updateFcmToken(request.getFcmToken());
        user.setOnboarding(onboarding);
        return user.getId();
    }

    public void existsByNickname(NicknameValidateRequestDto request) {
        if (onbordingRepository.existsByNickname(request.getNickname())) {
            throw new ConflictException(String.format("이미 존재하는 닉네임 (%s) 입니다", request.getNickname()),
                    CONFLICT_NICKNAME_EXCEPTION);
        }
    }
}
