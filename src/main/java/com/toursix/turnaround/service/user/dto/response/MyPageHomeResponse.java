package com.toursix.turnaround.service.user.dto.response;

import com.toursix.turnaround.domain.user.OnboardingProfileType;
import com.toursix.turnaround.domain.user.User;
import java.util.Map;
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

    private final static String AOS = "AOS";
    private final static String IOS = "iOS";

    private OnboardingProfileType profileType;
    private String nickname;
    private int point;
    private StoreUrl storeUrl;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class StoreUrl {

        private String aos;
        private String ios;

        public static StoreUrl of(String aosUrl, String iosUrl) {
            return StoreUrl.builder()
                    .aos(aosUrl)
                    .ios(iosUrl)
                    .build();
        }
    }

    public static MyPageHomeResponse of(User user, Map<String, String> deployByOs) {
        return MyPageHomeResponse.builder()
                .profileType(user.getOnboarding().getProfileType())
                .nickname(user.getOnboarding().getNickname())
                .point(user.getPoint().getAmount())
                .storeUrl(StoreUrl.of(deployByOs.get(AOS), deployByOs.get(IOS)))
                .build();
    }
}
