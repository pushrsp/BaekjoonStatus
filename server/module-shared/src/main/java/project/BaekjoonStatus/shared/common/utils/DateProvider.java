package project.BaekjoonStatus.shared.common.utils;

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

    public static LocalDateTime getNextCacheKey(LocalDateTime now) {
        LocalDateTime next = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 5,10,0);
        if(now.isBefore(next)) {
            return next;
        }

        return next.plusDays(1);
    }

    public static LocalDate getToday(LocalDateTime now) {
        return getNextCacheKey(now).minusDays(1).toLocalDate();
    }
}
