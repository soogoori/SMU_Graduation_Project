package graduation.shoewise.domain.purchase.dto;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.enums.Fit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    /*public static List<PurchaseResponseDto> of(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(purchase -> new PurchaseResponseDto())
    }*/

}
