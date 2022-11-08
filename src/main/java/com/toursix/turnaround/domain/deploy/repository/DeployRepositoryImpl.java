package com.toursix.turnaround.domain.deploy.repository;

import static com.toursix.turnaround.domain.deploy.QDeploy.deploy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.deploy.Deploy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeployRepositoryImpl implements DeployRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Deploy findDeployByOs(String os) {
        return queryFactory
                .selectFrom(deploy)
                .where(
                        deploy.os.eq(os),
                        deploy.status.eq(Status.ACTIVE)
                )
                .fetchOne();
    }
}
