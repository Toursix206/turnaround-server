package com.toursix.turnaround.service.activity;

import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.dto.request.GetActivitiesRequestDto;
import com.toursix.turnaround.service.activity.dto.response.ActivityInfoResponse;
import com.toursix.turnaround.service.activity.dto.response.ActivityPagingResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
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
}
