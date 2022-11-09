package com.toursix.turnaround.service.activity.dto.response;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import com.toursix.turnaround.domain.interior.InteriorType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityInfoResponse extends ActivityInfo {

    // TODO rewardItem 확정되면 수정하기
    private int point;
    private InteriorType rewardItem;

    @Builder(access = AccessLevel.PRIVATE)
    public ActivityInfoResponse(Long activityId, ActivityCategory category, ActivityType type, String name,
            int point, int broom, InteriorType rewardItem) {
        super(activityId, category, type, name, broom);
        this.point = point;
        this.rewardItem = rewardItem;
    }

    public static ActivityInfoResponse of(Activity activity) {
        return ActivityInfoResponse.builder()
                .activityId(activity.getId())
                .category(activity.getCategory())
                .type(activity.getType())
                .name(activity.getName())
                .point(activity.getPoint())
                .broom(activity.getBroom())
                .rewardItem(null)
                .build();
    }
}
