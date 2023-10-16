package graduation.shoewise.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AIReviewResponseDto {

    private Long shoeId;
    private String shoeName;
    private List<String> aiReviewContent;

    public AIReviewResponseDto(Long shoeId, String shoeName, List<String> aiReviewContent) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.aiReviewContent = aiReviewContent;
    }
}
