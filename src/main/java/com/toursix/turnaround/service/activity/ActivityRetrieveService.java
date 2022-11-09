package com.toursix.turnaround.service.activity;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityGuide;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.dto.request.GetActivitiesRequestDto;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse.ActivityGuideInfo;
import com.toursix.turnaround.service.activity.dto.response.ActivityInfoResponse;
import com.toursix.turnaround.service.activity.dto.response.ActivityPagingResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityRetrieveService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public ActivityPagingResponse getActivitiesUsingPaging(GetActivitiesRequestDto request,
            Pageable pageable, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        return ActivityPagingResponse.of(
                activityRepository.findActivitiesByFilterConditionUsingPaging(request.getType(),
                        request.getCategory(), pageable));
    }

    public ActivityInfoResponse getActivityInfo(Long activityId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        return ActivityInfoResponse.of(ActivityServiceUtils.findActivityById(activityRepository, activityId));
    }

    public ActivityGuideResponse getActivityGuide(Long activityId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        Activity activity = ActivityServiceUtils.findActivityById(activityRepository, activityId);
        List<ActivityGuide> activityGuide = activityRepository.findActivityGuidesByActivity(activity);
        if (activityGuide.isEmpty()) {
            throw new NotFoundException(String.format("존재하지 않는 활동 가이드 (%s) 입니다", activityId),
                    NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION);
        }
        return ActivityGuideResponse.of(activity.getId(), activity.getName(), ActivityGuideInfo.of(activityGuide));
    }
}
