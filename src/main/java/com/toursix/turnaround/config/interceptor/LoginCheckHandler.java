package com.toursix.turnaround.config.interceptor;

import com.toursix.turnaround.common.exception.UnAuthorizedException;
import com.toursix.turnaround.common.util.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {

    private final JwtUtils jwtProvider;

    public Long getUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring("Bearer ".length());
            if (jwtProvider.validateToken(accessToken)) {
                Long userId = jwtProvider.getUserIdFromJwt(accessToken);
                if (userId != null) {
                    return userId;
                }
            }
        }
        throw new UnAuthorizedException(String.format("잘못된 JWT (%s) 입니다.", bearerToken));
    }
}
