package graduation.shoewise.entity.user.dto;

import graduation.shoewise.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String nickname;
    private int size; // 발 길이
    private int width; // 발볼 너비 길이

    @Builder
    public UserRequestDto(String nickname, int size, int width) {
        this.nickname=nickname;
        this.size=size;
        this.width=width;
    }

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .size(size)
                .width(width)
                .build();
    }
}
