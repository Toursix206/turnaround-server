package com.toursix.turnaround.domain.todo.repository;

import com.toursix.turnaround.domain.todo.Todo;

public interface TodoRepositoryCustom {

    Todo findTodoById(Long todoId);
}
