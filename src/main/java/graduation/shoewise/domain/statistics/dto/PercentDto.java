package graduation.shoewise.domain.statistics.dto;

import com.querydsl.core.annotations.QueryProjection;
import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import lombok.Builder;
import lombok.Data;

public class PercentDto {

    @Data
    @Builder
    public static class FeelingPercentDto{
        Feeling feeling;
        double count;

        @QueryProjection
        public FeelingPercentDto(Feeling feeling, double count){
            this.feeling = feeling;
            this.count = count;
        }
    }

    @Data
    @Builder
    public static class FitPercentDto{
        Fit fit;
        double count;

        @QueryProjection
        public FitPercentDto(Fit fit, double count) {
            this.fit = fit;
            this.count = count;
        }
    }

    @Data
    @Builder
    public static class WidthDto{
        Width width;
        double count;

        @QueryProjection
        public WidthDto(Width width, double count) {
            this.width = width;
            this.count = count;
        }
    }
}
