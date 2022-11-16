package com.toursix.turnaround.domain.interior.repository;

import static com.toursix.turnaround.domain.interior.QObtain.obtain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.interior.Obtain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtainRepositoryImpl implements ObtainRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Obtain findObtainById(Long obtainId) {
        return queryFactory
                .selectFrom(obtain)
                .where(
                        obtain.id.eq(obtainId),
                        obtain.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
