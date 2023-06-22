package graduation.shoewise.domain.user.service;

import graduation.shoewise.domain.user.dto.UserRequestDto;
import graduation.shoewise.domain.user.dto.UserResponseDto;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static graduation.shoewise.global.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //

    // 유저 정보 조회
    public UserResponseDto getUserInfo(Long userId, Long id) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        return UserResponseDto.of(user, id);
    }

    // 내 정보 조회
    public UserResponseDto getMyInfo(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        return UserResponseDto.from(user);
    }

    // 유저 정보 변경
    @Transactional
    public void updateUser(Long userId, UserRequestDto request) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));
        user.update(request.toEntity());
    }

    // 유저 닉네임 중복 확인
    public boolean checkNickNameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }



    // 유저 구매이력 작성(변경)
    /*@Transactional
    public PurchaseResponseDto postPurchase(Long userId, PurchaseRequestDto request) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        user.update

    }*/


}
