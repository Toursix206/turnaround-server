package com.toursix.turnaround.service.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UrlInfo {

    private String aos;
    private String ios;

    public static UrlInfo of(String aosUrl, String iosUrl) {
        return UrlInfo.builder()
                .aos(aosUrl)
                .ios(iosUrl)
                .build();
    }
}
