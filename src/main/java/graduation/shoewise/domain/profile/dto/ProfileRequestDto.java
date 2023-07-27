package graduation.shoewise.domain.profile.dto;

import graduation.shoewise.domain.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value="UserRequestDto : 사용자 정보", description = "닉네임, 발길이, 발볼")
@Getter
@Setter
@NoArgsConstructor
public class ProfileRequestDto {

    private String nickname;
    private int size; // 발 길이
    private int width; // 발볼 너비 길이

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .size(size)
                .width(width)
                .build();
    }
}
