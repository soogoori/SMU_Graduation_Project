package graduation.shoewise.domain.statistics.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * graduation.shoewise.domain.statistics.dto.QPercentDto_FitPercentDto is a Querydsl Projection type for FitPercentDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPercentDto_FitPercentDto extends ConstructorExpression<PercentDto.FitPercentDto> {

    private static final long serialVersionUID = 1520593126L;

    public QPercentDto_FitPercentDto(com.querydsl.core.types.Expression<graduation.shoewise.domain.review.entity.Fit> fit, com.querydsl.core.types.Expression<Double> count) {
        super(PercentDto.FitPercentDto.class, new Class<?>[]{graduation.shoewise.domain.review.entity.Fit.class, double.class}, fit, count);
    }

}

