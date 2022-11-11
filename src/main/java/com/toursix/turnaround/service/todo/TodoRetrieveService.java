package com.toursix.turnaround.service.todo;

import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
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
        Todo todo = TodoServiceUtils.findTodoById(todoRepository, todoId);
        return TodoInfoResponse.of(todo);
    }
}
