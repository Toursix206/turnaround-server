package com.toursix.turnaround.domain.interior;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum InteriorCategoryType implements EnumModel {
    BED("침대"),
    TABLE("책상"),
    WALL("벽"),
    WINDOW("창문"),
    ;

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
