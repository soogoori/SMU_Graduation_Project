package graduation.shoewise.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class StatisticsDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeelingStatDto{
        private List<PercentDto.FeelingPercentDto> feelingStat;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FitStatDto{
        private List<PercentDto.FitPercentDto> fitStat;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WidthStatDto{
        private List<PercentDto.WidthDto> widthStat;
    }
}
