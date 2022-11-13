package com.toursix.turnaround.service.user.dto.response;

import com.toursix.turnaround.domain.user.OnboardingProfileType;
import com.toursix.turnaround.domain.user.User;
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
public class MyPageHomeResponse {

    private OnboardingProfileType profileType;
    private String nickname;
    private int point;

    public static MyPageHomeResponse of(User user) {
        return MyPageHomeResponse.builder()
                .profileType(user.getOnboarding().getProfileType())
                .nickname(user.getOnboarding().getNickname())
                .point(user.getPoint().getAmount())
                .build();
    }
}
