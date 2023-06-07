package graduation.shoewise.entity.shoes.dto;

import graduation.shoewise.entity.shoes.Shoes;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoesRegisterRequestDto {

    String name;
    String brand;

    public Shoes toEntity(){
        return Shoes.builder()
                .name(name)
                .brand(brand)
                .build();
    }
}
