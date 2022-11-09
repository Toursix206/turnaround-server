package com.toursix.turnaround.domain.activity.repository;

import static com.toursix.turnaround.domain.activity.QActivity.activity;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.activity.ActivityCategory;
import com.toursix.turnaround.domain.activity.ActivityType;
import com.toursix.turnaround.domain.common.Status;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Activity> findActivitiesByFilterConditionUsingPaging(ActivityType activityType,
            @Nullable ActivityCategory category, Pageable pageable) {
        List<OrderSpecifier> orders = getAllOrderSpecifiersByActivity(pageable);
        List<Activity> activities = queryFactory
                .selectFrom(activity).distinct()
                .where(
                        eqActivityType(activityType),
                        eqCategory(category),
                        activity.status.eq(Status.ACTIVE)
                )
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(activities, pageable, queryFactory
                .selectFrom(activity).distinct()
                .where(
                        eqActivityType(activityType),
                        eqCategory(category),
                        activity.status.eq(Status.ACTIVE)
                ).fetch().size());
    }

    private BooleanExpression eqActivityType(ActivityType type) {
        return activity.type.eq(type);
    }

    private BooleanExpression eqCategory(ActivityCategory category) {
        if (category == null) {
            return null;
        }
        return activity.category.eq(category);
    }

    private List<OrderSpecifier> getAllOrderSpecifiersByActivity(Pageable pageable) {
        List<OrderSpecifier> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            Path<Object> fieldPath = Expressions.path(Object.class, activity, order.getProperty());
            orders.add(new OrderSpecifier(direction, fieldPath));
        }
        return orders;
    }
}
