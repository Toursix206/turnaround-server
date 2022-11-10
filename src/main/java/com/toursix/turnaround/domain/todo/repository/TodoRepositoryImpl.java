package com.toursix.turnaround.domain.todo.repository;

import static com.toursix.turnaround.domain.todo.QTodo.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.todo.Todo;
import java.time.LocalDateTime;
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

    @Override
    public boolean existsByStartAtAndEndAt(LocalDateTime startAt, LocalDateTime endAt) {
        return queryFactory.selectOne()
                .from(todo)
                .where(
                        (todo.endAt.after(startAt).and(todo.endAt.before(endAt)).and(todo.status.eq(Status.ACTIVE)))
                                .or(todo.startAt.after(startAt).and(todo.startAt.before(endAt))
                                        .and(todo.status.eq(Status.ACTIVE)))
                                .or(todo.startAt.loe(startAt).and(todo.endAt.goe(endAt))
                                        .and(todo.status.eq(Status.ACTIVE)))
                )
                .fetchFirst() != null;
    }
}
