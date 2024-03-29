package com.toursix.turnaround.service.auth;

import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.common.RedisKey;
import com.toursix.turnaround.domain.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonAuthServiceUtils {

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
