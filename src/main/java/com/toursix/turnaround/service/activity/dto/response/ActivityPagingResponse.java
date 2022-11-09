package com.toursix.turnaround.service.activity.dto.response;

import com.toursix.turnaround.domain.activity.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityPagingResponse {

    private static final long LAST_PAGE = -1L;

    private List<ActivityInfoResponse> contents = new ArrayList<>();
    private long lastPage;
    private long nextPage;

    private ActivityPagingResponse(List<ActivityInfoResponse> contents, long lastPage, long nextPage) {
        this.contents = contents;
        this.lastPage = lastPage;
        this.nextPage = nextPage;
    }

    public static ActivityPagingResponse of(Page<Activity> activityPaging) {
        if (!activityPaging.hasNext()) {
            return ActivityPagingResponse.newLastScroll(activityPaging.getContent(),
                    activityPaging.getTotalPages() - 1);
        }
        return ActivityPagingResponse.newPagingHasNext(activityPaging.getContent(), activityPaging.getTotalPages() - 1,
                activityPaging.getPageable().getPageNumber() + 1);
    }

    private static ActivityPagingResponse newLastScroll(List<Activity> activityPaging, long lastPage) {
        return newPagingHasNext(activityPaging, lastPage, LAST_PAGE);
    }

    private static ActivityPagingResponse newPagingHasNext(List<Activity> activityPaging, long lastPage,
            long nextPage) {
        return new ActivityPagingResponse(getContents(activityPaging), lastPage, nextPage);
    }

    private static List<ActivityInfoResponse> getContents(List<Activity> activityPaging) {
        return activityPaging.stream()
                .map(ActivityInfoResponse::of)
                .collect(Collectors.toList());
    }
}
