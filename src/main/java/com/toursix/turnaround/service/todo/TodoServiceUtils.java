package com.toursix.turnaround.service.todo;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_TODO_TIME_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.FORBIDDEN_TODO_STAGE_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.NOT_FOUND_TODO_EXCEPTION;
import static com.toursix.turnaround.common.exception.ErrorCode.VALIDATION_TODO_START_AT_EXCEPTION;

import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.exception.ForbiddenException;
import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.todo.TodoStage;
import com.toursix.turnaround.domain.todo.repository.TodoRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoServiceUtils {

    public static void validateStartAt(LocalDateTime startAt, LocalDateTime now, int duration) {
        LocalDate today = now.toLocalDate();
        LocalDate afterTwoWeeks = today.plusDays(14);
        LocalDateTime endAt = startAt.plusMinutes(duration);

        // 활동 예약 종료 시간이 과거일 경우
        if (endAt.isBefore(now)) {
            throw new ValidationException(
                    String.format("정책에 위배되는 예약 시간입니다. startAt(%s) now(%s) duration(%s)", startAt, now, duration),
                    VALIDATION_TODO_START_AT_EXCEPTION);
        }

        // 활동의 시작 날짜와 종료 날짜가 다를 경우
        if (startAt.getDayOfMonth() != endAt.getDayOfMonth()) {
            throw new ValidationException(
                    String.format("정책에 위배되는 예약 시간입니다. startAt(%s) now(%s) duration(%s)", startAt, now, duration),
                    VALIDATION_TODO_START_AT_EXCEPTION);
        }

        // 2주 이후의 날짜에 대해 예약을 할 경우
        if (startAt.toLocalDate().isAfter(afterTwoWeeks)) {
            throw new ValidationException(
                    String.format("정책에 위배되는 예약 시간입니다. startAt(%s) now(%s) duration(%s)", startAt, now, duration),
                    VALIDATION_TODO_START_AT_EXCEPTION);
        }
    }

    public static void validateUniqueTodoTime(TodoRepository todoRepository, Onboarding onboarding,
            LocalDateTime startAt, LocalDateTime endAt) {
        if (todoRepository.existsByOnboardingAndStartAtAndEndAt(onboarding, startAt, endAt)) {
            throw new ConflictException(String.format("다른 활동과 겹치는 일정입니다. startAt(%s) endAt(%s)", startAt, endAt),
                    CONFLICT_TODO_TIME_EXCEPTION);
        }
    }

    public static Todo findTodoById(TodoRepository todoRepository, Long todoId) {
        Todo todo = todoRepository.findTodoById(todoId);
        if (todo == null) {
            throw new NotFoundException(String.format("존재하지 않는 todo (%s) 입니다", todoId), NOT_FOUND_TODO_EXCEPTION);
        }
        return todo;
    }

    public static void validateUpdatable(Todo todo) {
        if (todo.getStage() != TodoStage.IN_PROGRESS) {
            throw new ForbiddenException(String.format("수정/삭제 할 수 없는 일정 (%s) 입니다.", todo.getId()),
                    FORBIDDEN_TODO_STAGE_EXCEPTION);
        }
    }
}
