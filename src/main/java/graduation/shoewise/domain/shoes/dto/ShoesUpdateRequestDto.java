package graduation.shoewise.domain.shoes.dto;

import graduation.shoewise.domain.shoes.Shoes;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ShoesUpdateRequestDto {

    @NotNull(message = "제품 이름이 없습니다.")
    String name;

    @NotNull(message = "제품 이미지가 없습니다.")
    String image;

    String brand;

    String productCode;

    public Shoes toEntity(){
        return Shoes.builder()
                .name(name)
                .brand(brand)
                .image(image)
                .productCode(productCode)
                .build();
    }
}
