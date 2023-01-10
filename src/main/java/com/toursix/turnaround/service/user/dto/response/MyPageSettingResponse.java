package com.toursix.turnaround.service.user.dto.response;

import com.toursix.turnaround.domain.user.UrlType;
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
    private UrlInfo openSourceLicenseUrl;
    private String privateInformationPolicyUrl;

    public static MyPageSettingResponse of(boolean agreeAllPushNotification) {
        return MyPageSettingResponse.builder()
                .agreeAllPushNotification(agreeAllPushNotification)
                .openSourceLicenseUrl(UrlInfo.of(UrlType.AOS_OPENSOURCE_LICENSES.getValue(),
                        UrlType.IOS_OPENSOURCE_LICENSES.getValue()))
                .privateInformationPolicyUrl(UrlType.PRIVATE_INFORMATION_POLICY.getValue())
                .build();
    }
}
