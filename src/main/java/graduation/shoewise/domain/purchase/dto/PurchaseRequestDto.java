package graduation.shoewise.domain.purchase.dto;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDto {

    @NotNull(message = "제품 이름이 없습니다")
    private String name;

    @NotNull(message = "사이즈가 없습니다")
    private int size;

    @NotNull(message = "신발 정보가 없습니다")
    private Fit fit;

    public Purchase toEntity(User user){
        return Purchase.builder()
                .name(name)
                .size(size)
                .fit(fit)
                .user(user)
                .build();
    }
}
