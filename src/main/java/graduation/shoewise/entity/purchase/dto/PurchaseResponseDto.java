package graduation.shoewise.entity.purchase.dto;

import graduation.shoewise.entity.purchase.Purchase;
import graduation.shoewise.entity.enums.Fit;
import lombok.Getter;

@Getter
public class PurchaseResponseDto {

    private final String name;
    private final int size;
    private final String review;
    private final Fit fit;

    public PurchaseResponseDto(Purchase purchase) {
        this.name = purchase.getName();
        this.size = purchase.getSize();
        this.review = purchase.getReview();
        this.fit = purchase.getFit();
    }
}
