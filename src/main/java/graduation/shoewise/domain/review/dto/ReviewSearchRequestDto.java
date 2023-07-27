package graduation.shoewise.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewSearchRequestDto {

    private String query;
    private int size;

    public ReviewSearchRequestDto(String query, int size) {
        this.query = query;
        this.size = size;
    }
}
