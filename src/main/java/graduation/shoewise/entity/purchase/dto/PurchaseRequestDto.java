package graduation.shoewise.entity.purchase.dto;

import graduation.shoewise.entity.purchase.Purchase;
import graduation.shoewise.entity.enums.Fit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequestDto {

    private String name;
    private int size;
    private String review;
    private Fit fit;

    public PurchaseRequestDto(String name, int size, String review, Fit fit) {
        this.name = name;
        this.size = size;
        this.review = review;
        this.fit =fit;
    }

    public Purchase toEntity(){
        return Purchase.builder()
                .name(name)
                .size(size)
                .review(review)
                .fit(fit)
                .build();
    }
}
