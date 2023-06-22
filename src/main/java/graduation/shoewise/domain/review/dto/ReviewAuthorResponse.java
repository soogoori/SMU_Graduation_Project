package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewAuthorResponse {

    private Long id;
    private String profileImage;

    public static ReviewAuthorResponse from(User user){
        return new ReviewAuthorResponse(user.getId(), user.getProfileImage());
    }
}
