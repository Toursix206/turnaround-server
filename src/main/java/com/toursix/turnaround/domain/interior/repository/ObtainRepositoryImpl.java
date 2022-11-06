package com.toursix.turnaround.domain.interior.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtainRepositoryImpl implements ObtainRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
