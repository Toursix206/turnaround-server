package com.toursix.turnaround.domain.user.repository;

import static com.toursix.turnaround.domain.user.QOnboarding.onboarding;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnbordingRepositoryImpl implements OnbordingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByNickname(String nickname) {
        return queryFactory.selectOne()
                .from(onboarding)
                .where(
                        onboarding.nickname.eq(nickname),
                        onboarding.status.eq(Status.ACTIVE)
                ).fetchFirst() != null;
    }
}
