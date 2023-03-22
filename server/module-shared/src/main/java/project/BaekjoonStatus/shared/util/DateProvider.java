package project.BaekjoonStatus.shared.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateProvider {
    private static final String ZONE_ID = "Asia/Seoul";

    public static LocalDate getDate() {
        return LocalDate.now(ZoneId.of(ZONE_ID));
    }

    public static LocalDateTime getDateTime() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }
}
