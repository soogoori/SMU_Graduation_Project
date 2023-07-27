package graduation.shoewise.domain.profile.service;

import graduation.shoewise.domain.profile.dto.ProfileRequestDto;
import graduation.shoewise.domain.profile.dto.ProfileResponseDto;
import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.purchase.dto.PurchaseResponseDto;
import graduation.shoewise.domain.purchase.dto.PurchaseWithUserPageResponse;
import graduation.shoewise.domain.purchase.repository.PurchaseRepository;
import graduation.shoewise.domain.review.dto.ReviewWithUserPageResponseDto;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.review.dto.ReviewResponseDto;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.user.repository.UserRepository;
import graduation.shoewise.global.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import static graduation.shoewise.global.config.BaseResponseStatus.INVALID_USER_ID;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PurchaseRepository purchaseRepository;
    // private final S3Uploader s3Uploader;


    // 마이페이지 조회 - 내가 작성한 리뷰 목록
    public ReviewWithUserPageResponseDto getMyReview(Long userId, Pageable pageable) throws BaseException {
        Slice<Review> reviewList = reviewRepository.findAllByUserId(userId, pageable);

        return ReviewWithUserPageResponseDto.of(reviewList, userId);
    }

    // 마이페이지 조회 - 내가 구매한 목록
    public PurchaseWithUserPageResponse getMyPurchase(Long userId, Pageable pageable) {
        Slice<Purchase> purchaseList = purchaseRepository.findAllByUserId(userId, pageable);
        return PurchaseWithUserPageResponse.from(purchaseList);
    }

    // 프로필 수정
    @Transactional
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto request, MultipartFile multipartFile) throws BaseException {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        String image = "";
       /* if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "profile");
        }*/
        updateUser.updateNickName(request.getNickname());
        updateUser.updateSize(request.getSize(), request.getWidth());
        updateUser.updateProfileImage(image);

        return new ProfileResponseDto(updateUser);
    }

    // 유저 닉네임 중복 확인
    public boolean checkNickNameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
