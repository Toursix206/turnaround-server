package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.user.Onboarding;
import java.time.LocalDateTime;

public interface TodoRepositoryCustom {

    Todo findTodoById(Long todoId);

    boolean existsByOnboardingAndStartAtAndEndAt(Onboarding onboarding, LocalDateTime startAt, LocalDateTime endAt);
}
