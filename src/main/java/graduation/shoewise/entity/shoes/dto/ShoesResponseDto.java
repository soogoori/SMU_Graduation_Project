package graduation.shoewise.entity.shoes.dto;

import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ShoesResponseDto {
    private Long id;
    private String name;
    private String brand;
    private String image;
    private final List<ReviewResponseDto> reviewList = new ArrayList<>();


}
