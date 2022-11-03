package com.toursix.turnaround.service.auth;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.common.RedisKey;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonAuthServiceUtils {

    public static void resetConflictFcmToken(UserRepository userRepository, JwtUtils jwtProvider,
            String fcmToken) {
        User conflictFcmTokenUser = userRepository.findUserByFcmToken(fcmToken);
        if (conflictFcmTokenUser != null) {
            jwtProvider.expireRefreshToken(conflictFcmTokenUser.getId());
            conflictFcmTokenUser.resetFcmToken();
        }
    }

    public static void validateUniqueLogin(RedisTemplate<String, Object> redisTemplate, User user) {
        String refreshToken = (String) redisTemplate.opsForValue()
                .get(RedisKey.REFRESH_TOKEN + user.getId());
        if (refreshToken != null) {
            throw new ConflictException(String.format("이미 로그인된 유저 (%s) 입니다.", user.getId()),
                    ErrorCode.CONFLICT_LOGIN_EXCEPTION);
        }
    }

    public static void forceLogoutUser(RedisTemplate<String, Object> redisTemplate,
            JwtUtils jwtProvider, User user) {
        String refreshToken = (String) redisTemplate.opsForValue()
                .get(RedisKey.REFRESH_TOKEN + user.getId());
        if (refreshToken != null) {
            jwtProvider.expireRefreshToken(user.getId());
            user.resetFcmToken();
        }
    }
}
