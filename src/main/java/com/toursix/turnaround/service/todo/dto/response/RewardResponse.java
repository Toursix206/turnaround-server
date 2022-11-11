package com.toursix.turnaround.service.todo.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class RewardResponse {

    private int broom;

    public static RewardResponse of(int broom) {
        return RewardResponse.builder()
                .broom(broom)
                .build();
    }
}
