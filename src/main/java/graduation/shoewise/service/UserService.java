package graduation.shoewise.service;

import graduation.shoewise.config.BaseException;
import graduation.shoewise.entity.user.dto.UserRequestDto;
import graduation.shoewise.entity.user.dto.UserResponseDto;
import graduation.shoewise.entity.user.User;
import graduation.shoewise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static graduation.shoewise.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 정보 조회
    public UserResponseDto getUser(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        return new UserResponseDto(user);
    }

    // 유저 정보 변경
    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto request) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        user.updateNickName(request.getNickname());
        user.updateSize(request.getSize(), request.getWidth());

        return new UserResponseDto(user);
    }

    // 유저 구매이력 작성(변경)
    /*@Transactional
    public PurchaseResponseDto postPurchase(Long userId, PurchaseRequestDto request) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        user.update

    }*/


}
