package graduation.shoewise.domain.purchase.controller;

import graduation.shoewise.domain.purchase.dto.PurchaseRequestDto;
import graduation.shoewise.domain.purchase.dto.PurchaseResponseDto;
import graduation.shoewise.domain.purchase.service.PurchaseService;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.global.security.UserPrincipal;
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
    public PurchaseResponseDto savePurchase(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody PurchaseRequestDto requestDto) throws BaseException {
        return purchaseService.save(userPrincipal.getId(), requestDto);
    }

    // 구매목록 조회 (전체 조회)
    @ApiOperation(value = "get purchase", notes = "구매목록 조회")
    @GetMapping
    public Page<PurchaseResponseDto> purchaseList(@PageableDefault(size = 5) Pageable pageable) {
        return purchaseService.get(pageable);
    }

    // 구매목록 삭제
    @ApiOperation(value = "delete purchase", notes = "구매목록 삭제")
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponseDto> deletePurchase(@PathVariable Long purchaseId,
                                                              @AuthenticationPrincipal UserPrincipal userPrincipal) throws BaseException {
        return ResponseEntity.ok(purchaseService.delete(userPrincipal.getId(), purchaseId));
    }


    // 구매목록 수정
    @ApiOperation(value = "update purchase", notes = "구매목록 수정")
    @PutMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponseDto> updatePurchase(@PathVariable Long purchaseId,
                                                              @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                              @RequestBody PurchaseRequestDto requestDto) throws BaseException {
        return ResponseEntity.ok(purchaseService.updatePurchase(userPrincipal.getId(), purchaseId, requestDto));
    }
}
