package graduation.shoewise.domain.shoes.dto;

import graduation.shoewise.domain.shoes.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShoesResponseDto {
    private Long id;
    private String name;
    private String productCode;
    private String brand;
    private String image;
    private int reviewCnt;
    private double avgRating;

    public static ShoesResponseDto from(Shoes shoes) {
        return new ShoesResponseDto(
                shoes.getId(),
                shoes.getName(),
                shoes.getProductCode(),
                shoes.getBrand(),
                shoes.getImage(),
                shoes.getReviewCount(),
                shoes.getAvgRating()
        );
    }
}
