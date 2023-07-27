package graduation.shoewise.domain.shoes.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoesRequestDto {
    private String name;
    private String brand;
}
