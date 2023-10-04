package graduation.shoewise.domain.shoes.dto;

import graduation.shoewise.domain.shoes.Shoes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShoesRegisterResponseDto {

    private Long id;
    private String name;
    private String brand;
    private String image;
    private String productCode;
    //private List<ReviewResponseDto> reviewList = new ArrayList<>();

    public ShoesRegisterResponseDto(Shoes shoes){
        this.id = shoes.getId();
        this.name=shoes.getName();
        this.brand=shoes.getBrand();
        this.image = shoes.getImage();
        this.productCode = shoes.getProductCode();
    }
}
