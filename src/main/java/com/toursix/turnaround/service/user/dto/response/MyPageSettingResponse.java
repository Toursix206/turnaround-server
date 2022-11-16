package com.toursix.turnaround.service.user.dto.response;

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
public class MyPageSettingResponse {

    private Boolean agreeAllPushNotification;

    public static MyPageSettingResponse of(boolean agreeAllPushNotification) {
        return MyPageSettingResponse.builder()
                .agreeAllPushNotification(agreeAllPushNotification)
                .build();
    }
}
