package com.toursix.turnaround.service.todo;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityGuide;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.todo.DoneReview;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.repository.DoneReviewRepository;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.ActivityServiceUtils;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse;
import com.toursix.turnaround.service.activity.dto.response.ActivityGuideResponse.ActivityGuideInfo;
import com.toursix.turnaround.service.todo.dto.response.DoneReviewInfoResponse;
import com.toursix.turnaround.service.todo.dto.response.TodoInfoResponse;
import com.toursix.turnaround.service.todo.dto.response.TodoMainResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TodoRetrieveService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final ActivityRepository activityRepository;
    private final DoneReviewRepository doneReviewRepository;

    public TodoMainResponse getTodoMainInfo(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        LocalDateTime now = DateUtils.todayLocalDateTime();
        List<Todo> todos = user.getOnboarding().getTodos();
        List<Todo> inProgressTodayOrFutureTodos = TodoServiceUtils.filterInProgressTodayOrFutureTodos(now, todos);
        List<Todo> successTodos = TodoServiceUtils.filterSuccessTodos(todos);
        List<Todo> todayTodos = TodoServiceUtils.filterTodayTodos(now, inProgressTodayOrFutureTodos);
        List<Todo> thisWeekTodos = TodoServiceUtils.filterThisWeekTodos(now, inProgressTodayOrFutureTodos);
        List<Todo> nextTodos = TodoServiceUtils.filterNextTodos(now, inProgressTodayOrFutureTodos);
        return TodoMainResponse.of(now, successTodos, todayTodos, thisWeekTodos, nextTodos);
    }

    public TodoInfoResponse getTodoInfo(Long todoId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        LocalDateTime now = DateUtils.todayLocalDateTime();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        return TodoInfoResponse.of(now, todo);
    }

    public void getTodoAbleStart(Long todoId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        Activity activity = todo.getActivity();
        LocalDateTime now = DateUtils.todayLocalDateTime();
        if (now.isBefore(todo.getStartAt())) {
            TodoValidateUtils.validateUniqueTodoTime(todoRepository, onboarding, todo, now,
                    now.plusMinutes(activity.getDuration()));
        }
    }

    public ActivityGuideResponse getTodoGuide(Long todoId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        Activity activity = todo.getActivity();
        ActivityServiceUtils.findActivityById(activityRepository, activity.getId());
        List<ActivityGuide> activityGuide = activityRepository.findActivityGuidesByActivity(activity);
        if (activityGuide.isEmpty()) {
            throw new NotFoundException(String.format("존재하지 않는 활동 가이드 (%s) 입니다", activity.getId()),
                    NOT_FOUND_ACTIVITY_GUIDE_EXCEPTION);
        }
        return ActivityGuideResponse.of(activity.getId(), activity.getName(), ActivityGuideInfo.of(activityGuide));
    }

    public DoneReviewInfoResponse getTodoDoneReview(Long doneReviewId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        DoneReview doneReview = TodoServiceUtils.findDoneReviewById(doneReviewRepository, doneReviewId);
        return DoneReviewInfoResponse.of(doneReview);
    }
}
