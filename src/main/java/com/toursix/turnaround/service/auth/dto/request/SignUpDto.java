package com.toursix.turnaround.service.auth.dto.request;

import com.toursix.turnaround.domain.user.OnboardingProfileType;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.service.user.dto.request.CreateUserRequestDto;
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
public class SignUpDto {

    private UserSocialType socialType;
    private String token;
    private String fcmToken;
    private OnboardingProfileType profileType;
    private String nickname;

    public static SignUpDto of(UserSocialType socialType, String token, String fcmToken,
            OnboardingProfileType profileType, String nickname) {
        return SignUpDto.builder()
                .socialType(socialType)
                .token(token)
                .fcmToken(fcmToken)
                .profileType(profileType)
                .nickname(nickname)
                .build();
    }

    public CreateUserRequestDto toCreateUserDto(String socialId) {
        return CreateUserRequestDto.of(socialId, socialType, fcmToken, profileType, nickname);
    }
}
