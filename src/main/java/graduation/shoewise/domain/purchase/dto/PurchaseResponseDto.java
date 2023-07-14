package graduation.shoewise.domain.purchase.dto;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.review.entity.Fit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseResponseDto {

    private final Long id;
    private final String name;
    private final int size;
    private final Fit fit;

    public PurchaseResponseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.name = purchase.getName();
        this.size = purchase.getSize();
        this.fit = purchase.getFit();
    }

    public static PurchaseResponseDto from(Purchase purchase) {
        return new PurchaseResponseDto(
                purchase.getId(),
                purchase.getName(),
                purchase.getSize(),
                purchase.getFit()
        );
    }
}
