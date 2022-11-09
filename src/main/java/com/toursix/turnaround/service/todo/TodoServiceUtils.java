package com.toursix.turnaround.service.todo;

import com.toursix.turnaround.common.exception.ErrorCode;
import com.toursix.turnaround.common.exception.ForbiddenException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoServiceUtils {

    public static void validateStartAt(LocalDateTime startAt, LocalDateTime now, int duration) {
        LocalDate today = now.toLocalDate();
        LocalDate afterTwoWeeks = today.plusDays(14);
        LocalDateTime endAt = startAt.plusMinutes(duration);

        // 활동이 시작 날짜와 종료 날짜가 다를 경우
        if (startAt.getDayOfMonth() != endAt.getDayOfMonth()) {
            throw new ForbiddenException(
                    String.format("정책에 위배되는 예약 시간입니다. startAt(%s) now(%s) duration(%s)", startAt, now, duration),
                    ErrorCode.FORBIDDEN_TODO_START_AT_EXCEPTION);
        }

        // 2주 이후의 날짜에 대해 예약을 할 경우
        if (startAt.toLocalDate().isAfter(afterTwoWeeks)) {
            throw new ForbiddenException(
                    String.format("정책에 위배되는 예약 시간입니다. startAt(%s) now(%s) duration(%s)", startAt, now, duration),
                    ErrorCode.FORBIDDEN_TODO_START_AT_EXCEPTION);
        }
    }
}
