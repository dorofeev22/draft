package ru.dorofeev22.draft.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    
    private static final DateTimeFormatter ISO_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    
    public static LocalDateTime fromIso(final String time) {
        return LocalDateTime.parse(time, ISO_TIME);
    }

    public static String getNowAsInstant() {
        return String.valueOf(Instant.now().toEpochMilli());
    }
}
