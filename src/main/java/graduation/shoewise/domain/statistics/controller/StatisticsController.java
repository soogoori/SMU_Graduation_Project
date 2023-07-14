package graduation.shoewise.domain.statistics.controller;

import graduation.shoewise.domain.shoes.dto.ShoesStatisticsResponse;
import graduation.shoewise.domain.statistics.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/shoes/{shoesId}/statistics")
    public ShoesStatisticsResponse getStatistics(@PathVariable final Long shoesId){
        return statisticsService.
    }
}
