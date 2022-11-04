package com.toursix.turnaround.domain.activity;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ActivityCategory implements EnumModel {
    BEDDING("침구류"),
    TABLE("책상"),
    WASHER("세탁기"),
    KITCHEN("주방"),
    RESTROOM("화장실"),
    SELF_DEVELOPMENT("자기개발"),
    ETC("기타");

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
