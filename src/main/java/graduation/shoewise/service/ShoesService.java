package graduation.shoewise.service;

import graduation.shoewise.entity.enums.RoleType;
import graduation.shoewise.entity.shoes.Shoes;
import graduation.shoewise.entity.shoes.dto.ShoesRegisterRequestDto;
import graduation.shoewise.entity.shoes.dto.ShoesRegisterResponseDto;
import graduation.shoewise.entity.shoes.dto.ShoesResponseDto;
import graduation.shoewise.entity.user.User;
import graduation.shoewise.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoesService {

    private final ShoesRepository shoesRepository;

    // 관리자 상품 등록
    @Transactional
    public ShoesRegisterResponseDto registerShoes(ShoesRegisterRequestDto requestDto){
        Shoes shoes = shoesRepository.save(requestDto.toEntity());
        return new ShoesRegisterResponseDto(shoes);
    }

    // 관리자 상품 삭제
    @Transactional
    public void deleteShoes(Long shoesId, User user) {
        Shoes shoes;

        if (user.getRole().equals(RoleType.ADMIN)) {
            shoes = shoesRepository.findById(shoesId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

            shoesRepository.delete(shoes);
        }
    }

    // 신발(게시물) 조회
    public ShoesResponseDto getShoes(Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
        return new ShoesResponseDto(); // 여기에 리뷰 볼 수 있도록.....???
    }

    // 게시물 상세 조회

    // 카테고리별 게시물 출력

    // 게시물 업데이트

    //게시글 전체 출력 페이징 처리





}
