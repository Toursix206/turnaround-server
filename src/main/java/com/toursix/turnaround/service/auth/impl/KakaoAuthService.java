package com.toursix.turnaround.service.auth.impl;

import com.toursix.turnaround.common.util.HttpHeaderUtils;
import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.external.client.kakao.KakaoApiClient;
import com.toursix.turnaround.external.client.kakao.dto.response.KakaoProfileResponse;
import com.toursix.turnaround.service.auth.AuthService;
import com.toursix.turnaround.service.auth.CommonAuthService;
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
public class KakaoAuthService implements AuthService {

    private static final UserSocialType socialType = UserSocialType.KAKAO;

    private final KakaoApiClient kakaoApiCaller;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommonAuthService commonAuthService;
    private final JwtUtils jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long signUp(SignUpDto request) {
        KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(
                HttpHeaderUtils.withBearerToken(request.getToken()));
        return userService.registerUser(request.toCreateUserDto(response.getId()));
    }

    @Override
    public Long login(LoginDto request) {
        KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(
                HttpHeaderUtils.withBearerToken(request.getToken()));
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository,
                response.getId(), socialType);
        commonAuthService.resetConflictFcmToken(request.getFcmToken());
        CommonAuthServiceUtils.validateUniqueLogin(redisTemplate, user);
        user.updateFcmToken(request.getFcmToken());
        return user.getId();
    }

    @Override
    public Long forceLogin(LoginDto request) {
        KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(
                HttpHeaderUtils.withBearerToken(request.getToken()));
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository,
                response.getId(), socialType);
        commonAuthService.resetConflictFcmToken(request.getFcmToken());
        CommonAuthServiceUtils.forceLogoutUser(redisTemplate, jwtProvider, user);
        user.updateFcmToken(request.getFcmToken());
        return user.getId();
    }
}
