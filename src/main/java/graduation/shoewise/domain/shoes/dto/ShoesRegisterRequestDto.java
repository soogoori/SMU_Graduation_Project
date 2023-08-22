package graduation.shoewise.domain.shoes.dto;

import graduation.shoewise.domain.shoes.Shoes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ShoesRegisterRequestDto {

    @NotNull(message = "제품 이름이 없습니다.")
    String name;

    /*@NotNull(message = "제품 이미지가 없습니다.")
    String image;*/

    String brand;

    public Shoes toEntity(String image){
        return Shoes.builder()
                .name(name)
                .brand(brand)
                .image(image)
                .build();
    }
}
