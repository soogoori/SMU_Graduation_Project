package graduation.shoewise.domain.shoes.dto;

import graduation.shoewise.domain.shoes.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ShoesPageResponse {

    private boolean hasNext;
    private List<ShoesResponseDto> shoes;

    public static ShoesPageResponse from(Slice<Shoes> slice) {
        List<ShoesResponseDto> shoesResponses = slice.getContent()
                .stream()
                .map(ShoesResponseDto::from)
                .collect(Collectors.toList());
        return new ShoesPageResponse(slice.hasNext(), shoesResponses);
    }
}
