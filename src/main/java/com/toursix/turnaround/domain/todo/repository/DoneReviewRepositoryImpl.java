package com.toursix.turnaround.domain.todo.repository;

import static com.toursix.turnaround.domain.todo.QDoneReview.doneReview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.todo.DoneReview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoneReviewRepositoryImpl implements DoneReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DoneReview findDoneReviewById(Long doneReviewId) {
        return queryFactory.selectFrom(doneReview)
                .where(doneReview.id.eq(doneReviewId))
                .fetchOne();
    }
}
