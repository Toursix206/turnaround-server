package com.toursix.turnaround.domain.space.repository;

import static com.toursix.turnaround.domain.space.QSpace.space;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.space.Space;
import com.toursix.turnaround.domain.space.SpaceCategory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpaceRepositoryImpl implements SpaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Space findSpaceBySpaceCategory(SpaceCategory spaceCategory) {
        return queryFactory
                .selectFrom(space)
                .where(
                        space.category.eq(spaceCategory),
                        space.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
