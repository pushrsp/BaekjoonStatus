package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.DailyCountDto;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryByUserId;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatService {
    private final SolvedHistoryService solvedHistoryService;

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        if(year.isEmpty())
            year = String.valueOf(DateProvider.getDate().getYear());

       return solvedHistoryService.getSolvedCountGroupByDate(userId, year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        List<CountByLevel> solvedCountGroupByLevel = solvedHistoryService.getSolvedCountGroupByLevel(userId);
        Map<String, Long> map = new HashMap<>();

        for (CountByLevel countByLevel : solvedCountGroupByLevel) {
            if(map.containsKey(countByLevel.getLevel())) {
                map.replace(countByLevel.getLevel(), map.get(countByLevel.getLevel()) + countByLevel.getCount());
            } else {
                map.put(countByLevel.getLevel(), countByLevel.getCount());
            }
        }

        return map.keySet().stream()
                .map((k) -> new CountByLevel(k, map.get(k)))
                .toList();
    }

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return solvedHistoryService.getSolvedCountGroupByTag(userId);
    }

    public List<SolvedHistoryByUserId> getSolvedHistoriesByUserId(String userId, Integer offset) {
        return solvedHistoryService.findSolvedHistoriesByUserId(userId, offset);
    }
}
