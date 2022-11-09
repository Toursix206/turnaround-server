package com.toursix.turnaround.service.activity;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_ACTIVITY_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityServiceUtils {

    public static Activity findActivityById(ActivityRepository activityRepository, Long activityId) {
        Activity activity = activityRepository.findActivityById(activityId);
        if (activity == null) {
            throw new NotFoundException(String.format("존재하지 않는 활동 (%s) 입니다", activityId), NOT_FOUND_ACTIVITY_EXCEPTION);
        }
        return activity;
    }
}
