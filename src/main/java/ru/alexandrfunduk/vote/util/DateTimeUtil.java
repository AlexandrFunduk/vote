package ru.alexandrfunduk.vote.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private DateTimeUtil() {
    }

    public static LocalDate atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS) : MAX_DATE;
    }
}
