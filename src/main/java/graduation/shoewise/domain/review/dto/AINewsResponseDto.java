package graduation.shoewise.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AINewsResponseDto {

    private Long shoeId;
    private String shoeName;
    private List<String> aiNewsContent;

    public AINewsResponseDto(Long shoeId, String shoeName, List<String> aiNewsContent) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.aiNewsContent = aiNewsContent;
    }
}
