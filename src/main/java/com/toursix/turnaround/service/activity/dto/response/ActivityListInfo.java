package com.toursix.turnaround.service.activity.dto.response;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityListInfo extends ActivityInfo {

    private int duration;
    private String imageUrl;
    private String description;

    @Builder(access = AccessLevel.PRIVATE)
    public ActivityListInfo(Long activityId, ActivityCategory category, ActivityType type, String name, int duration,
            String imageUrl, String description, int broom) {
        super(activityId, category, type, name, broom);
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static ActivityListInfo of(@NotNull Activity activity) {
        return ActivityListInfo.builder()
                .activityId(activity.getId())
                .category(activity.getCategory())
                .type(activity.getType())
                .name(activity.getName())
                .description(activity.getDescription())
                .duration(activity.getDuration())
                .imageUrl(activity.getImageUrl())
                .broom(activity.getBroom())
                .build();
    }
}
