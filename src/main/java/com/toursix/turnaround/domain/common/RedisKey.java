package com.toursix.turnaround.domain.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RedisKey {

    public static final String REFRESH_TOKEN = "RT:";
}
