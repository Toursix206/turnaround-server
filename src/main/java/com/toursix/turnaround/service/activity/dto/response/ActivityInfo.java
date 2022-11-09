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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class ActivityInfo {

    private Long activityId;
    private ActivityCategory category;
    private ActivityType type;
    private String name;
    private int broom;

    public static ActivityInfo of(@NotNull Activity activity) {
        return ActivityInfo.builder()
                .activityId(activity.getId())
                .category(activity.getCategory())
                .type(activity.getType())
                .name(activity.getName())
                .broom(activity.getBroom())
                .build();
    }
}
