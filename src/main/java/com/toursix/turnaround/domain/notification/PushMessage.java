package com.toursix.turnaround.domain.notification;

import com.toursix.turnaround.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PushMessage implements EnumModel {

    BEFORE_ONE_HOUR_TODO("오늘의 활동 시작", "님 1시간 뒤 오늘의 턴어라운드를 시작해요"),
    START_TODO("오늘의 활동 시작", "지금 바로 활동을 시작하세요."),
    REMIND_TODO("미완료 활동 알림", "혹시, 잊으신건가요?\n오늘까지 활동을 완료해 보세요."),
    REMIND_REWARD("활동 리워드 받기", "님, 축하해요!\n활동 리워드는 받으셨나요?"),
    INDUCE_NEW_TODO("새로운 활동 예약", "님, 어제는 바쁘셨나요?\n오늘은 턴어라운드 활동을 예약해봐요!"),
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
