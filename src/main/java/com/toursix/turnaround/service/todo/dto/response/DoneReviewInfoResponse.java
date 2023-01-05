package com.toursix.turnaround.service.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.todo.DoneReview;
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
public class DoneReviewInfoResponse {

    private String name;
    private String imageUrl;
    private String doneDate;
    private int score;
    private String content;
    private boolean isWritten;
    private int point;

    @JsonProperty("isWritten")
    public boolean isWritten() {
        return isWritten;
    }

    public static DoneReviewInfoResponse of(DoneReview doneReview) {
        return DoneReviewInfoResponse.builder()
                .name(doneReview.getActivity().getName())
                .imageUrl(doneReview.getDone().getImageUrl())
                .doneDate(DateUtils.parseYearAndMonthAndDay(doneReview.getCreatedAt()))
                .score(doneReview.getRating().getScore())
                .content(doneReview.getContent())
                .isWritten(doneReview.getIsWritten())
                .point(doneReview.getActivity().getPoint())
                .build();
    }
}
