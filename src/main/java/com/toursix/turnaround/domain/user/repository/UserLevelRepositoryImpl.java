package com.toursix.turnaround.domain.user.repository;


import static com.toursix.turnaround.domain.user.QUserLevel.userLevel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.user.UserLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLevelRepositoryImpl implements UserLevelRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserLevel findLevelByExperience(int experience) {
        return queryFactory
                .selectFrom(userLevel)
                .where(
                        userLevel.experience.gt(experience),
                        userLevel.status.eq(Status.ACTIVE)
                )
                .orderBy(userLevel.experience.asc())
                .fetchOne();
    }
}
