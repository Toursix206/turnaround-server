package com.toursix.turnaround.service.auth;

import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.service.auth.impl.AppleAuthService;
import com.toursix.turnaround.service.auth.impl.KakaoAuthService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthServiceProvider {

    private static final Map<UserSocialType, AuthService> authServiceMap = new HashMap<>();

    private final AppleAuthService appleAuthService;
    private final KakaoAuthService kakaoAuthService;

    @PostConstruct
    void initializeAuthServicesMap() {
        authServiceMap.put(UserSocialType.APPLE, appleAuthService);
        authServiceMap.put(UserSocialType.KAKAO, kakaoAuthService);
    }

    public AuthService getAuthService(UserSocialType socialType) {
        return authServiceMap.get(socialType);
    }
}
