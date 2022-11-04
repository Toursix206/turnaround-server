package com.toursix.turnaround.domain.user.repository;

import static com.toursix.turnaround.domain.user.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBySocialIdAndSocialType(String socialId, UserSocialType socialType) {
        return queryFactory.selectOne()
                .from(user)
                .where(
                        user.socialInfo.socialId.eq(socialId),
                        user.socialInfo.socialType.eq(socialType),
                        user.status.eq(Status.ACTIVE)
                ).fetchFirst() != null;
    }

    @Override
    public User findUserById(Long id) {
        return queryFactory
                .selectFrom(user)
                .where(
                        user.id.eq(id),
                        user.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }

    @Override
    public User findUserBySocialIdAndSocialType(String socialId, UserSocialType socialType) {
        return queryFactory
                .selectFrom(user)
                .where(
                        user.socialInfo.socialId.eq(socialId),
                        user.socialInfo.socialType.eq(socialType),
                        user.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }

    @Override
    public User findUserByFcmToken(String fcmToken) {
        return queryFactory
                .selectFrom(user)
                .where(
                        user.fcmToken.eq(fcmToken),
                        user.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
