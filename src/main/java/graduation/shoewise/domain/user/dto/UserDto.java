package graduation.shoewise.domain.user.dto;

import graduation.shoewise.domain.enums.RoleType;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;
    private RoleType role;
}
