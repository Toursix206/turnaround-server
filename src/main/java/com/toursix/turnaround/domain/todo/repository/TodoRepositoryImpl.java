package com.toursix.turnaround.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
