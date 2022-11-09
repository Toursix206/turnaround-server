package com.toursix.turnaround.service.todo;

import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.repository.ActivityRepository;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.activity.ActivityServiceUtils;
import com.toursix.turnaround.service.todo.dto.request.CreateTodoRequestDto;
import com.toursix.turnaround.service.todo.dto.request.UpdateTodoRequestDto;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TodoService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final TodoRepository todoRepository;

    public void createTodo(CreateTodoRequestDto request, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Activity activity = ActivityServiceUtils.findActivityById(activityRepository, request.getActivityId());
        LocalDateTime now = DateUtils.todayLocalDateTime();
        TodoServiceUtils.validateStartAt(request.getStartAt(), now, activity.getDuration());
        todoRepository.save(
                Todo.newInstance(user.getOnboarding(), activity, request.getStartAt(), request.getPushStatus()));
    }

    public void updateTodo(UpdateTodoRequestDto request, Long todoId, Long userId) {
        UserServiceUtils.findUserById(userRepository, userId);
        LocalDateTime now = DateUtils.todayLocalDateTime();
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        TodoServiceUtils.validateUpdatable(todo);
        TodoServiceUtils.validateStartAt(request.getStartAt(), now, todo.getActivity().getDuration());
        todo.updateStartAt(request.getStartAt());
        todo.updatePushStatus(request.getPushStatus());
    }
}
