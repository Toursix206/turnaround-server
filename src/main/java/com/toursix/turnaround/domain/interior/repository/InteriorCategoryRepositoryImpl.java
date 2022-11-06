package com.toursix.turnaround.domain.interior.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InteriorCategoryRepositoryImpl implements InteriorCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
