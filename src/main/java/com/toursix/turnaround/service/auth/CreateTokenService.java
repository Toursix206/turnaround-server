package com.toursix.turnaround.service.auth;

import com.toursix.turnaround.common.exception.UnAuthorizedException;
import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.service.auth.dto.request.TokenRequestDto;
import com.toursix.turnaround.service.auth.dto.response.RefreshResponse;
import com.toursix.turnaround.service.auth.dto.response.TokenResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateTokenService {

    private final JwtUtils jwtProvider;
    private final RedisTemplate redisTemplate;

    @Transactional
    public TokenResponse createTokenInfo(Long userId) {
        return jwtProvider.createTokenInfo(userId);
    }

    @Transactional
    public RefreshResponse reissueToken(TokenRequestDto request) {
        if (!jwtProvider.validateToken(request.getRefreshToken())) {
            throw new UnAuthorizedException(
                    String.format("주어진 리프레시 토큰 (%s) 이 유효하지 않습니다.", request.getRefreshToken()));
        }
        Long userId = jwtProvider.getUserIdFromJwt(request.getAccessToken());
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + userId);

        if (Objects.isNull(refreshToken)) {
            throw new UnAuthorizedException(
                    String.format("이미 만료된 리프레시 토큰 (%s) 입니다.", request.getRefreshToken()));
        }
        if (!refreshToken.equals(request.getRefreshToken())) {
            throw new UnAuthorizedException(
                    String.format("해당 리프레시 토큰의 정보가 일치하지 않습니다.", request.getRefreshToken()));
        }
        return RefreshResponse.of(jwtProvider.createTokenInfo(userId));
    }
}
