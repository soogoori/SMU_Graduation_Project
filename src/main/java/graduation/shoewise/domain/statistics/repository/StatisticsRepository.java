package graduation.shoewise.domain.statistics.repository;

import graduation.shoewise.domain.statistics.entity.FeelingCount;
import graduation.shoewise.domain.statistics.entity.FitCount;
import graduation.shoewise.domain.statistics.entity.WidthCount;

import java.util.List;

public interface StatisticsRepository {

    List<FeelingCount> findFeelingCountByShoesId(Long shoesId);

    List<FitCount> findFitCountByShoesId(Long shoesId);

    List<WidthCount> findWidthCountByShoesId(Long shoesId);
}
