package com.toursix.turnaround.domain.interior;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum InteriorType implements EnumModel {
    BASIC_BED("기본 침대"),
    BASIC_TABLE("기본 책상"),
    BASIC_WALL("기본 벽"),
    BASIC_WINDOW("기본 창문"),
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
