package com.toursix.turnaround.domain.user;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UrlType implements EnumModel {
    // 오픈소스 라이선스 링크
    AOS_OPENSOURCE_LICENSES("https://turnaround.notion.site/AOS-1-0-0-19c201bc78a846b2b094476891c04d28"),
    IOS_OPENSOURCE_LICENSES("https://turnaround.notion.site/iOS-1-0-0-d427f6dc77364b328bff3645854e713f"),

    // 개인정보 처리 방침 및 약관 링크
    PRIVATE_INFORMATION_POLICY("https://turnaround.notion.site/36a436b90e93430ca34308f40e3fe4a8"),

    // 고객센터 링크
    CUSTOMER_SERVICE("http://pf.kakao.com/_xjQBRxj/chat");

    private final String value;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
