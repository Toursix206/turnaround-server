package com.toursix.turnaround.domain.done.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoneReviewRepositoryImpl implements DoneReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
