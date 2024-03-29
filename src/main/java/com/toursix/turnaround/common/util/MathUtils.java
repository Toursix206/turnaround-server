package com.toursix.turnaround.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

    public static int percent(int part, int total) {
        return (int) ((double) part / (double) total * 100);
    }

    public static int average(int total, int count) {
        return total / count;
    }
}
