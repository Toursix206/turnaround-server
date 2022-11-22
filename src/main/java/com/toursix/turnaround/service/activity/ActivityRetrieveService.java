package com.toursix.turnaround.service.activity;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_TIME_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityGuide;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.dto.request.GetActivitiesRequestDto;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse.ActivityGuideInfo;
import com.toursix.turnaround.service.activity.dto.response.ActivityPagingResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
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
    private final TodoRepository todoRepository;

    public ActivityPagingResponse getActivitiesUsingPaging(GetActivitiesRequestDto request,
            Pageable pageable, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        return ActivityPagingResponse.of(
                activityRepository.findActivitiesByFilterConditionUsingPaging(request.getType(),
                        request.getCategory(), pageable));
    }

    public ActivityGuideResponse getActivityGuide(Long activityId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Activity activity = ActivityServiceUtils.findActivityById(activityRepository, activityId);
        LocalDateTime now = DateUtils.todayLocalDateTime();
        if (!todoRepository.existsByOnboardingAndActivityStartAt(onboarding, activity, now)) {
            throw new ConflictException(String.format("다른 활동과 겹치는 일정입니다. startAt(%s)", now),
                    CONFLICT_TODO_TIME_EXCEPTION);
        }
        List<ActivityGuide> activityGuide = activityRepository.findActivityGuidesByActivity(activity);
        if (activityGuide.isEmpty()) {
            throw new NotFoundException(String.format("존재하지 않는 활동 가이드 (%s) 입니다", activityId),
                    NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION);
        }
        return ActivityGuideResponse.of(activity.getId(), activity.getName(), ActivityGuideInfo.of(activityGuide));
    }
}
