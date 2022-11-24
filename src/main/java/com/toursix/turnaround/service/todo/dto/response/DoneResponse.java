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
public class DoneResponse {

    private Long doneReviewId;

    public static DoneResponse of(Long doneReviewId) {
        return DoneResponse.builder()
                .doneReviewId(doneReviewId)
                .build();
    }
}
