package graduation.shoewise.domain.user.dto;

import graduation.shoewise.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotNull(message = "닉네임이 없습니다.")
    private String nickname;

    @NotNull(message = "발 사이즈 정보가 없습니다.")
    private int size;

    @NotNull(message = "발볼 정보가 없습니다.")
    private int width;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .size(size)
                .width(width)
                .build();
    }
}
