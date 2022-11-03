package com.toursix.turnaround.service.user.dto.request;

import com.toursix.turnaround.domain.user.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateUserRequestDto {

    private String socialId;
    private UserSocialType socialType;
    private String fcmToken;

    public static CreateUserRequestDto of(String socialId, UserSocialType socialType,
            String fcmToken) {
        return CreateUserRequestDto.builder()
                .socialId(socialId)
                .socialType(socialType)
                .fcmToken(fcmToken)
                .build();
    }
}
