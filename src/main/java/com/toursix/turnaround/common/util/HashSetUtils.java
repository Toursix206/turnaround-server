package com.toursix.turnaround.common.util;

import java.util.Random;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashSetUtils {

    public static <T> T getRandomItem(Set<T> set) {
        int size = set.size();

        int currIdx = 0;
        int randIdx = new Random().nextInt(size);

        for (T t : set) {
            if (currIdx == randIdx) {
                return t;
            }
            currIdx += 1;
        }
        return null;
    }
}
