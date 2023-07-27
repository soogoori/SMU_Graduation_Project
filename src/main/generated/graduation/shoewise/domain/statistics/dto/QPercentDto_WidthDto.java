package graduation.shoewise.domain.statistics.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * graduation.shoewise.domain.statistics.dto.QPercentDto_WidthDto is a Querydsl Projection type for WidthDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPercentDto_WidthDto extends ConstructorExpression<PercentDto.WidthDto> {

    private static final long serialVersionUID = 1609282238L;

    public QPercentDto_WidthDto(com.querydsl.core.types.Expression<graduation.shoewise.domain.review.entity.Width> width, com.querydsl.core.types.Expression<Double> count) {
        super(PercentDto.WidthDto.class, new Class<?>[]{graduation.shoewise.domain.review.entity.Width.class, double.class}, width, count);
    }

}

