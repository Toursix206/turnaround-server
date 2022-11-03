package com.toursix.turnaround.controller.auth.dto.response;

import com.toursix.turnaround.service.auth.dto.response.TokenResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private Long userId;
    private TokenResponse token;

    public static LoginResponse of(Long userId, TokenResponse token) {
        return LoginResponse.builder()
                .userId(userId)
                .token(token)
                .build();
    }
}
