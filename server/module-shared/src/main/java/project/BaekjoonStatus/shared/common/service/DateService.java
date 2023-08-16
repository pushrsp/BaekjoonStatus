package project.BaekjoonStatus.shared.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateService {
    LocalDate getDate();
    LocalDate getToday(LocalDateTime now);
    LocalDateTime getDateTime();
    LocalDateTime getNextCacheKey(LocalDateTime now);
}
