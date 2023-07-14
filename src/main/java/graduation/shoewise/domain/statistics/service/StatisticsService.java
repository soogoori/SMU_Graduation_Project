package graduation.shoewise.domain.statistics.service;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.domain.statistics.entity.FeelingCount;
import graduation.shoewise.domain.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final ShoesRepository shoesRepository;
    private final StatisticsRepository statisticsRepository;

    public StatisticsService(ShoesRepository shoesRepository, StatisticsRepository statisticsRepository) {
        this.shoesRepository = shoesRepository;
        this.statisticsRepository = statisticsRepository;
    }

    private Map<Feeling, Double> calculateWithFeeling(Long shoesId) {
        List<FeelingCount> feelingCounts = statisticsRepository.findFeelingCountByShoesId(shoesId);

    }
}
