package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.user.User;
import lombok.Getter;

@Getter
public class ReviewAuthorResponseDto {

    private final Long id;
    private final String nickname;
    private final String profileImage;

    public ReviewAuthorResponseDto(Long id, String nickname, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static ReviewAuthorResponseDto from(User user) {
        return new ReviewAuthorResponseDto(user.getId(), user.getNickname(), user.getProfileImage());
    }
}
