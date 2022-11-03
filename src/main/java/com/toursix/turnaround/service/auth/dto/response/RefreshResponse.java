package com.toursix.turnaround.service.auth.dto.response;

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
public class RefreshResponse {

    private TokenResponse token;

    public static RefreshResponse of(TokenResponse token) {
        return RefreshResponse.builder()
                .token(token)
                .build();
    }
}
