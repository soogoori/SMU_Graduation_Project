package graduation.shoewise.domain.purchase.controller;

import graduation.shoewise.domain.purchase.dto.PurchaseRequestDto;
import graduation.shoewise.domain.purchase.dto.PurchaseResponseDto;
import graduation.shoewise.domain.purchase.dto.PurchaseWithUserPageResponse;
import graduation.shoewise.domain.purchase.service.PurchaseService;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.global.security.oauth.service.SecurityUtil;
import graduation.shoewise.global.security.oauth.service.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    // 구매목록 등록
    @ApiOperation(value = "save purchase", notes = "구매목록 등록")
    @PostMapping
    public PurchaseResponseDto savePurchase(@RequestBody PurchaseRequestDto requestDto) throws BaseException {

        Long userId = SecurityUtil.getCurrentMemberPk();
        return purchaseService.save(userId, requestDto);
    }

    // 구매목록 조회 (전체 조회)
    @ApiOperation(value = "get purchase", notes = "구매목록 조회")
    @GetMapping
    public PurchaseWithUserPageResponse purchaseList(Pageable pageable) {
        return purchaseService.get(pageable);
    }

    // 다른 사용자의 구매목록 조회
    @ApiOperation(value = "get purchase from users", notes = "다른 사용자 구매목록 조회")
    @GetMapping("/user/{userId}")
    public PurchaseWithUserPageResponse purchaseListWithUser(Pageable pageable, @PathVariable Long userId){
        return purchaseService.getWithUser(userId, pageable);
    }

    // 구매목록 삭제
    @ApiOperation(value = "delete purchase", notes = "구매목록 삭제")
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponseDto> deletePurchase(@PathVariable Long purchaseId) throws BaseException {
        Long userId = SecurityUtil.getCurrentMemberPk();
        return ResponseEntity.ok(purchaseService.delete(userId, purchaseId));
    }


    // 구매목록 수정
    @ApiOperation(value = "update purchase", notes = "구매목록 수정")
    @PutMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponseDto> updatePurchase(@PathVariable Long purchaseId,
                                                              @RequestBody PurchaseRequestDto requestDto) throws BaseException {

        Long userId = SecurityUtil.getCurrentMemberPk();
        return ResponseEntity.ok(purchaseService.updatePurchase(userId, purchaseId, requestDto));
    }
}
