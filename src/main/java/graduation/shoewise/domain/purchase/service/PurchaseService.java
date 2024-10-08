package graduation.shoewise.domain.purchase.service;

import graduation.shoewise.domain.purchase.dto.PurchaseWithUserPageResponse;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.purchase.dto.PurchaseRequestDto;
import graduation.shoewise.domain.purchase.dto.PurchaseResponseDto;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.purchase.repository.PurchaseRepository;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static graduation.shoewise.global.config.BaseResponseStatus.INVALID_USER_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;


    // 구매목록 조회
    public PurchaseWithUserPageResponse get(Pageable pageable) {

        Slice<Purchase> purchaseList = purchaseRepository.findAll(pageable);
        return PurchaseWithUserPageResponse.from(purchaseList);
    }

    // 사용자별 구매목록 조회
    public PurchaseWithUserPageResponse getWithUser(Long userId, Pageable pageable){
        Slice<Purchase> purchaseList = purchaseRepository.findAllByUserId(userId, pageable);
        return PurchaseWithUserPageResponse.from(purchaseList);
    }

    // 구매목록 등록
    @Transactional
    public PurchaseResponseDto save(Long userId, PurchaseRequestDto requestDto) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        log.info("구매목록을 등록하는 유저 : " + user.getEmail());

        Purchase purchase = purchaseRepository.save(requestDto.toEntity(user));

        return new PurchaseResponseDto(purchase);
    }

    // 구매목록 삭제
    @Transactional
    public PurchaseResponseDto delete(Long userId, Long purchaseId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        log.info("구매목록을 삭제할거에요");
        log.info("user's id : " + user.getId());
        log.info("user's email : " + user.getEmail());

        Purchase purchase = purchaseRepository.findByIdAndUserId(purchaseId, user.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 구매목록이 없습니다. purchaseId=" + purchaseId));


        purchaseRepository.delete(purchase);

        return new PurchaseResponseDto(purchase);
    }

    // 구매목록 수정
    @Transactional
    public PurchaseResponseDto updatePurchase(Long userId, Long purchaseId, PurchaseRequestDto requestDto) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        Purchase purchase = purchaseRepository.findByIdAndUserId(purchaseId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 구매목록이 없습니다. purchaseId=" + purchaseId));

        purchase.update(requestDto);

        return new PurchaseResponseDto(purchase);
    }

    public void validateUser(Long userId) throws BaseException{
        if (!userRepository.existsById(userId)) {
            throw new BaseException(INVALID_USER_ID);
        }
    }
}
