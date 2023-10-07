package project.BaekjoonStatus.batch.common.service;

import project.BaekjoonStatus.shared.common.service.DateService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class KrDateService implements DateService {

    private static final String ZONE_ID = "Asia/Seoul";

    @Override
    public LocalDate getDate() {
        return LocalDate.now(ZoneId.of(ZONE_ID));
    }

    @Override
    public LocalDate getToday(LocalDateTime now) {
        return getNextCacheKey(now).minusDays(1).toLocalDate();
    }

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }

    @Override
    public LocalDateTime getNextCacheKey(LocalDateTime now) {
        LocalDateTime next = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 5,10,0);
        if(now.isBefore(next)) {
            return next;
        }

        return next.plusDays(1);
    }
}
