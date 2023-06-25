package graduation.shoewise.global.security.dto;

import graduation.shoewise.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private Long id;
    private String nickname;
    private RoleType roleType;
    private String email;

    private String appToken;
    private Boolean isNewMember;
}
