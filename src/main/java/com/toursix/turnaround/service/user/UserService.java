package com.toursix.turnaround.service.user;

import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.user.dto.request.CreateUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtProvider;

    @Transactional
    public Long registerUser(CreateUserRequestDto request) {
        UserServiceUtils.validateNotExistsUser(userRepository, request.getSocialId(),
                request.getSocialType());
        User user = userRepository.save(
                User.newInstance(request.getSocialId(), request.getSocialType()));
        User conflictFcmTokenUser = userRepository.findUserByFcmToken(request.getFcmToken());
        if (conflictFcmTokenUser != null) {
            jwtProvider.expireRefreshToken(conflictFcmTokenUser.getId());
            conflictFcmTokenUser.resetFcmToken();
        }
        user.updateFcmToken(request.getFcmToken());
        return user.getId();
    }

    public void existsByNickname(NicknameValidateRequestDto request) {
        if (onbordingRepository.existsByNickname(request.getNickname())) {
            throw new ConflictException(String.format("이미 존재하는 닉네임 (%s) 입니다", request.getNickname()),
                    CONFLICT_NICKNAME_EXCEPTION);
        }
    }
}
