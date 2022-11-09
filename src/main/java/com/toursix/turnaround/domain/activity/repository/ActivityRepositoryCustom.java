package com.toursix.turnaround.domain.activity.repository;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityGuide;
import com.toursix.turnaround.domain.activity.ActivityType;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityRepositoryCustom {

    Page<Activity> findActivitiesByFilterConditionUsingPaging(ActivityType type,
            @Nullable ActivityCategory category, Pageable pageable);

    Activity findActivityById(Long activityId);

    List<ActivityGuide> findActivityGuidesByActivity(Activity activity);
}
