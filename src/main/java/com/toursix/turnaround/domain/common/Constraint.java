package com.toursix.turnaround.domain.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Constraint {

    public static final int ONBOARDING_NICKNAME_MAX = 8;
    public static final int DONE_REVIEW_CONTENT_MIN = 10;
}
