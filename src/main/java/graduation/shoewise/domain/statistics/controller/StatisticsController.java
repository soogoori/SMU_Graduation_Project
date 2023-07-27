package graduation.shoewise.domain.statistics.controller;

import graduation.shoewise.domain.review.dto.ReviewStatisticsResponseDto;
import graduation.shoewise.domain.statistics.dto.PercentDto;
import graduation.shoewise.domain.statistics.dto.StatisticsDto;
import graduation.shoewise.domain.statistics.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/shoes/{shoesId}/statistics")
    public ReviewStatisticsResponseDto getStatistics(@PathVariable final Long shoesId){
        List<PercentDto.FitPercentDto> fitPercent = statisticsService.getFitPercent(shoesId);
        List<PercentDto.FeelingPercentDto> feelingPercent = statisticsService.getFeelingPercent(shoesId);
        List<PercentDto.WidthDto> widthPercent = statisticsService.getWidthPercent(shoesId);

        return ReviewStatisticsResponseDto.builder()
                .feelingPercent(feelingPercent)
                .fitPercent(fitPercent)
                .widthPercent(widthPercent)
                .build();

    }
}
