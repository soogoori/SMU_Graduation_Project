package graduation.shoewise.entity.shoes.dto;

import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import graduation.shoewise.entity.shoes.Shoes;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ShoesRegisterResponseDto {

    private Long id;
    private String name;
    private String brand;
    private String image;
    //private List<ReviewResponseDto> reviewList = new ArrayList<>();

    public ShoesRegisterResponseDto(Shoes shoes){
        this.id = shoes.getId();
        this.name=shoes.getName();
        this.brand=shoes.getBrand();
        this.image = shoes.getImage();
    }
}
