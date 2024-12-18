package com.api.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    private static final String DATE_NONE = "날짜 없음";

    public static String formatCreatedDate(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return DATE_NONE;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        return localDateTime.format(formatter);
    }
}
