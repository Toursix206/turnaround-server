package com.toursix.turnaround.domain.todo.repository;

import static com.toursix.turnaround.domain.todo.QTodo.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.todo.Todo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Todo findTodoById(Long todoId) {
        return queryFactory
                .selectFrom(todo)
                .where(
                        todo.id.eq(todoId),
                        todo.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
