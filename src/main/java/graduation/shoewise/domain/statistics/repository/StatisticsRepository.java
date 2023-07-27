package graduation.shoewise.domain.statistics.repository;

import graduation.shoewise.domain.statistics.dto.PercentDto;

import java.util.List;

public interface StatisticsRepository {

    List<PercentDto.FeelingPercentDto> findFeelingCountByShoesId(Long shoesId);

    List<PercentDto.FitPercentDto> findFitCountByShoesId(Long shoesId);

    List<PercentDto.WidthDto> findWidthCountByShoesId(Long shoesId);
}
