package com.toursix.turnaround.domain.interior;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CleanLevel implements EnumModel {
    CLEAN("가장 깨끗한 에셋", 100, 61),
    DIRTY("더러운 에셋", 60, 31),
    VERY_DIRTY("제일 더러운 에셋", 30, 0),
    ;

    private final String value;
    private final int max;
    private final int min;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    public CleanLevel increase() {
        if (this == DIRTY) {
            return CLEAN;
        }
        if (this == VERY_DIRTY) {
            return DIRTY;
        }
        return CLEAN;
    }
}
