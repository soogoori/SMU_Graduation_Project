package graduation.shoewise.domain.profile.dto;

import graduation.shoewise.domain.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value="MyPageResponseDto : 사용자 프로필 정보", description = "닉네임, 발길이, 발볼")
@Getter
public class ProfileResponseDto {


    @ApiModelProperty(value = "회원 닉네임")
    private final String nickname;

    @ApiModelProperty(value = "발 사이즈")
    private final int size;

    @ApiModelProperty(value = "발볼")
    private final int width;

    @ApiModelProperty(value = "프로필 이미지")
    private final String profileImage;


    public ProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.size = user.getSize();
        this.width = user.getWidth();

    }
}
