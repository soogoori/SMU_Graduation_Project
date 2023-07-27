package graduation.shoewise.domain.statistics.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * graduation.shoewise.domain.statistics.dto.QPercentDto_FeelingPercentDto is a Querydsl Projection type for FeelingPercentDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPercentDto_FeelingPercentDto extends ConstructorExpression<PercentDto.FeelingPercentDto> {

    private static final long serialVersionUID = 386848273L;

    public QPercentDto_FeelingPercentDto(com.querydsl.core.types.Expression<graduation.shoewise.domain.review.entity.Feeling> feeling, com.querydsl.core.types.Expression<Double> count) {
        super(PercentDto.FeelingPercentDto.class, new Class<?>[]{graduation.shoewise.domain.review.entity.Feeling.class, double.class}, feeling, count);
    }

}

