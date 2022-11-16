package com.toursix.turnaround.domain.notification;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PushMessage implements EnumModel {

    BEFORE_ONE_HOUR_TODO("오늘의 활동 시작", "님 1시간 뒤 오늘의 턴어라운드를 시작해요"),
    ;

    private final String title;
    private final String body;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return title;
    }
}
