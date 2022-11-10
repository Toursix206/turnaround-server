package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.Todo;
import java.time.LocalDateTime;

public interface TodoRepositoryCustom {

    Todo findTodoById(Long todoId);

    boolean existsByStartAtAndEndAt(LocalDateTime startAt, LocalDateTime endAt);
}
