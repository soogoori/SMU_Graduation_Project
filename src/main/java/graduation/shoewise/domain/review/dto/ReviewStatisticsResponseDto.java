package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.statistics.dto.PercentDto;
import graduation.shoewise.domain.statistics.dto.StatisticsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatisticsResponseDto {
    private List<PercentDto.FeelingPercentDto> feelingPercent;
    private List<PercentDto.FitPercentDto> fitPercent;
    private List<PercentDto.WidthDto> widthPercent;
}
