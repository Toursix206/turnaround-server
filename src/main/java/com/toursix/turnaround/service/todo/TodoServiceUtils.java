package com.toursix.turnaround.service.todo;

import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_TODO_EXCEPTION;

import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.common.util.DateUtils;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStage;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoServiceUtils {

    public static Todo findTodoById(TodoRepository todoRepository, Long todoId) {
        Todo todo = todoRepository.findTodoById(todoId);
        if (todo == null) {
            throw new NotFoundException(String.format("존재하지 않는 todo (%s) 입니다", todoId), NOT_FOUND_TODO_EXCEPTION);
        }
        return todo;
    }

    public static List<Todo> filterSuccessTodos(List<Todo> todos) {
        return todos.stream()
                .filter(todo -> todo.getStage() == TodoStage.SUCCESS)
                .collect(Collectors.toList());
    }

    public static List<Todo> filterInProgressTodayTodos(LocalDateTime now, List<Todo> todos) {
        return todos.stream()
                .filter(todo -> todo.getStage() == TodoStage.IN_PROGRESS &&
                        DateUtils.isSameDate(now, todo.getStartAt())
                )
                .collect(Collectors.toList());
    }

    public static List<Todo> filterInProgressTodayOrFutureTodos(LocalDateTime now, List<Todo> todos) {
        return todos.stream()
                .filter(todo -> todo.getStage() == TodoStage.IN_PROGRESS &&
                        DateUtils.isTodayOrFuture(now, todo.getStartAt())
                )
                .collect(Collectors.toList());
    }

    public static List<Todo> filterTodayTodos(LocalDateTime now, List<Todo> todos) {
        return todos.stream()
                .filter(todo -> DateUtils.isSameDate(now, todo.getStartAt()))
                .collect(Collectors.toList());
    }

    public static List<Todo> filterThisWeekTodos(LocalDateTime now, List<Todo> todos) {
        return todos.stream()
                .filter(todo -> !DateUtils.isSameDate(now, todo.getStartAt()) &&
                        DateUtils.isSameWeek(now, todo.getStartAt())
                )
                .collect(Collectors.toList());
    }

    public static List<Todo> filterNextTodos(LocalDateTime now, List<Todo> todos) {
        return todos.stream()
                .filter(todo -> !DateUtils.isSameWeek(now, todo.getStartAt()))
                .collect(Collectors.toList());
    }

    public static String leftTime(LocalDateTime now, Todo todo) {
        if (todo.getStage() == TodoStage.SUCCESS) {
            return "눌러서 리워드 받기";
        }
        if (now.isAfter(todo.getEndAt())) {
            return "오늘까지";
        }
        if (now.isAfter(todo.getStartAt())) {
            return "지금 시작하세요!";
        }
        Duration duration = Duration.between(now, todo.getStartAt());
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days == 0) {
            return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
        }
        Period period = Period.between(now.toLocalDate(), todo.getStartAt().toLocalDate());
        return "D-" + period.getDays();
    }
}
