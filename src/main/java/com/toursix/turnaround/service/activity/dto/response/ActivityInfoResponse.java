package com.toursix.turnaround.service.activity.dto.response;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ActivityInfoResponse {

    private Long activityId;
    private ActivityCategory category;
    private ActivityType activityType;
    private String name;
    private String imageUrl;
    private int broom;

    public static ActivityInfoResponse of(@NotNull Activity activity) {
        return ActivityInfoResponse.builder()
                .activityId(activity.getId())
                .category(activity.getCategory())
                .activityType(activity.getType())
                .name(activity.getName())
                .imageUrl(activity.getImageUrl())
                .broom(activity.getBroom())
                .build();
    }
}
