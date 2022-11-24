package com.toursix.turnaround.common.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private static final WeekFields WEEK_FIELDS = WeekFields.of(DayOfWeek.MONDAY, 7);

    public static LocalDate todayLocalDate() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }

    public static LocalDateTime todayLocalDateTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public static String parseYearAndMonthAndDay(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return date.format(formatter);
    }

    public static LocalDate yesterdayLocalDate() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return today.minusDays(1);
    }

    public static String parseMonthAndDay(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
        return date.format(formatter);
    }

    public static String nowDayOfWeek(LocalDate now) {
        return now.getDayOfWeek().toString();
    }

    public static boolean isSameDate(LocalDateTime date1, LocalDateTime date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date1.format(formatter).equals(date2.format(formatter));
    }

    public static boolean isSameWeek(LocalDateTime date1, LocalDateTime date2) {
        return date1.get(WEEK_FIELDS.weekOfWeekBasedYear()) == date2.get(WEEK_FIELDS.weekOfWeekBasedYear());
    }

    public static boolean isTodayOrFuture(LocalDateTime now, LocalDateTime date) {
        return isSameDate(now, date) || date.isAfter(now);
    }

    public static boolean isBeforeOneHour(LocalDateTime now, LocalDateTime date) {
        Duration duration = Duration.between(now.truncatedTo(ChronoUnit.MINUTES), date.truncatedTo(ChronoUnit.MINUTES));
        return now.isBefore(date) && duration.getSeconds() == 60 * 60;
    }

    public static boolean isSameTime(LocalDateTime now, LocalDateTime date) {
        return now.truncatedTo(ChronoUnit.MINUTES).isEqual(date.truncatedTo(ChronoUnit.MINUTES));
    }
}
