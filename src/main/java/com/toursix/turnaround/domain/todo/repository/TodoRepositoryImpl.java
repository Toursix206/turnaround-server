package com.toursix.turnaround.domain.todo.repository;

import static com.toursix.turnaround.domain.todo.QTodo.todo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.todo.Todo;
import com.toursix.turnaround.domain.user.Onboarding;
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
    public boolean existsByOnboardingAndStartAtAndEndAt(Onboarding onboarding, LocalDateTime startAt,
            LocalDateTime endAt) {
        return queryFactory.selectOne()
                .from(todo)
                .where(
                        todo.onboarding.eq(onboarding),
                        todo.status.eq(Status.ACTIVE),
                        startAtAndEndAtAreBetween(startAt, endAt)
                                .or(startAtIsAfterStartAtAndBeforeEndAt(startAt, endAt))
                                .or(endAtIsAfterStartAtAndBeforeEndAt(startAt, endAt))
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByOnboardingAndActivityStartAt(Onboarding onboarding, Activity activity,
            LocalDateTime startAt) {
        return !queryFactory.selectFrom(todo)
                .where(
                        todo.onboarding.eq(onboarding),
                        todo.status.eq(Status.ACTIVE),
                        todo.activity.eq(activity),
                        todo.startAt.loe(startAt)
                ).fetch().isEmpty();
    }

    private BooleanExpression startAtAndEndAtAreBetween(LocalDateTime startAt, LocalDateTime endAt) {
        return todo.startAt.loe(startAt).and(todo.endAt.goe(endAt));
    }

    private BooleanExpression startAtIsAfterStartAtAndBeforeEndAt(LocalDateTime startAt, LocalDateTime endAt) {
        return todo.startAt.after(startAt).and(todo.startAt.before(endAt));
    }

    private BooleanExpression endAtIsAfterStartAtAndBeforeEndAt(LocalDateTime startAt, LocalDateTime endAt) {
        return todo.endAt.after(startAt).and(todo.endAt.before(endAt));
    }
}
