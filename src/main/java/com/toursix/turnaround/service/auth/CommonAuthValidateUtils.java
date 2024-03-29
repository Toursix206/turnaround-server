package com.toursix.turnaround.service.auth;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_LOGIN_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.domain.common.RedisKey;
import com.toursix.turnaround.domain.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonAuthValidateUtils {

    public static void validateUniqueLogin(RedisTemplate<String, Object> redisTemplate, User user) {
        String refreshToken = (String) redisTemplate.opsForValue()
                .get(RedisKey.REFRESH_TOKEN + user.getId());
        if (refreshToken != null) {
            throw new ConflictException(String.format("이미 로그인된 유저 (%s) 입니다.", user.getId()),
                    CONFLICT_LOGIN_EXCEPTION);
        }
    }
}
