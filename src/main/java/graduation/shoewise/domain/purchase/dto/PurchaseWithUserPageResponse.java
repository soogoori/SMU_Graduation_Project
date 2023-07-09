package graduation.shoewise.domain.purchase.dto;

import graduation.shoewise.domain.purchase.Purchase;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PurchaseWithUserPageResponse {

    private final boolean hasNext;
    private final List<PurchaseResponseDto> purchase;

    public PurchaseWithUserPageResponse(boolean hasNext, List<PurchaseResponseDto> purchase) {
        this.hasNext = hasNext;
        this.purchase = purchase;
    }

    public static PurchaseWithUserPageResponse from(Slice<Purchase> slice) {
        List<PurchaseResponseDto> purchaseList = slice.getContent()
                .stream()
                .map(PurchaseResponseDto::from)
                .collect(Collectors.toList());

        return new PurchaseWithUserPageResponse(slice.hasNext(), purchaseList);
    }
}
