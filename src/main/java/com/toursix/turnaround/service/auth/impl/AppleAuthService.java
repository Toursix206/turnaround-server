package com.toursix.turnaround.service.auth.impl;

import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.external.client.apple.AppleTokenProvider;
import com.toursix.turnaround.service.auth.AuthService;
import com.toursix.turnaround.service.auth.CommonAuthServiceUtils;
import com.toursix.turnaround.service.auth.dto.request.LoginDto;
import com.toursix.turnaround.service.auth.dto.request.SignUpDto;
import com.toursix.turnaround.service.user.UserService;
import com.toursix.turnaround.service.user.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AppleAuthService implements AuthService {

    private static final UserSocialType socialType = UserSocialType.APPLE;

    private final AppleTokenProvider appleTokenDecoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtils jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long signUp(SignUpDto request) {
        String socialId = appleTokenDecoder.getSocialIdFromIdToken(request.getToken());
        return userService.registerUser(request.toCreateUserDto(socialId));
    }

    @Override
    public Long login(LoginDto request) {
        String socialId = appleTokenDecoder.getSocialIdFromIdToken(request.getToken());
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, socialId,
                socialType);
        CommonAuthServiceUtils.resetConflictFcmToken(userRepository, jwtProvider,
                request.getFcmToken());
        CommonAuthServiceUtils.validateUniqueLogin(redisTemplate, user);
        user.updateFcmToken(request.getFcmToken());
        return user.getId();
    }

    @Override
    public Long forceLogin(LoginDto request) {
        String socialId = appleTokenDecoder.getSocialIdFromIdToken(request.getToken());
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, socialId,
                socialType);
        CommonAuthServiceUtils.resetConflictFcmToken(userRepository, jwtProvider,
                request.getFcmToken());
        CommonAuthServiceUtils.forceLogoutUser(redisTemplate, jwtProvider, user);
        user.updateFcmToken(request.getFcmToken());
        return user.getId();
    }
}
