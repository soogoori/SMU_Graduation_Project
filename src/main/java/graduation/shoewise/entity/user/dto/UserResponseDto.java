package graduation.shoewise.entity.user.dto;

import graduation.shoewise.entity.purchase.Purchase;
import graduation.shoewise.entity.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {

    private final Long userId;
    private final String nickname;
    private final String providerId;
    private final int size;
    private final int width;
    private final List<Purchase> purchaseList;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.size = user.getSize();
        this.providerId = user.getProviderId();
        this.width = user.getWidth();
        this.purchaseList = user.getPurchaseList();
    }
}
