package com.toursix.turnaround.domain.space.repository;

import static com.toursix.turnaround.domain.space.QSpaceCategory.spaceCategory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.space.SpaceCategory;
import com.toursix.turnaround.domain.space.SpaceCategoryType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpaceCategoryRepositoryImpl implements SpaceCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public SpaceCategory findSpaceCategoryByName(SpaceCategoryType spaceCategoryType) {
        return queryFactory
                .selectFrom(spaceCategory)
                .where(
                        spaceCategory.name.eq(spaceCategoryType),
                        spaceCategory.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
