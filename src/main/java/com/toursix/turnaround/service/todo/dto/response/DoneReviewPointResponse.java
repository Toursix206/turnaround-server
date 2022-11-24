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
public class DoneReviewPointResponse {

    private String name;
    private String doneDate;
    private int point;

    public static DoneReviewPointResponse of(String name, String doneDate, int point) {
        return DoneReviewPointResponse.builder()
                .name(name)
                .doneDate(doneDate)
                .point(point)
                .build();
    }
}
