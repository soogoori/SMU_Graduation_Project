package graduation.shoewise.domain.user.dto;

import graduation.shoewise.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDto {

    private boolean isMine;
    private Long id;
    private String nickname;
    private String profileImage;
    private int size;
    private int width;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                true,
                user.getId(),
                user.getNickname(),
                user.getProfileImage(),
                user.getSize(),
                user.getWidth()
        );
    }

    // Entity -> DTO
    public static UserResponseDto of(User user, Long userId) {
        return new UserResponseDto(
                user.isSameId(userId),
                user.getId(), user.getNickname(), user.getProfileImage(),
                user.getSize(), user.getWidth());
    }
}
