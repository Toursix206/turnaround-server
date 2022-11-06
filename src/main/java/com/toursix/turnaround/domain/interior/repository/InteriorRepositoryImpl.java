package com.toursix.turnaround.domain.interior.repository;

import static com.toursix.turnaround.domain.interior.QInterior.interior;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.interior.Interior;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InteriorRepositoryImpl implements InteriorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Interior findInteriorByName(String name) {
        return queryFactory
                .selectFrom(interior)
                .where(
                        interior.name.eq(name),
                        interior.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
