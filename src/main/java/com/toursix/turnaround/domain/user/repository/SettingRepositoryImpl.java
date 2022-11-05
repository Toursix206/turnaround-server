package com.toursix.turnaround.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SettingRepositoryImpl implements SettingRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
