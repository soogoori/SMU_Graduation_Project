package graduation.shoewise.domain.shoes.service;

import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.shoes.dto.ShoesPageResponse;
import graduation.shoewise.domain.shoes.dto.ShoesRegisterRequestDto;
import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import graduation.shoewise.domain.shoes.dto.ShoesUpdateRequestDto;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.global.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoesService {

    private final ShoesRepository shoesRepository;
    private final ReviewRepository reviewRepository;
    private final S3Uploader s3Uploader;


    // 관리자 상품 등록
    @Transactional
    public Long registerShoes(ShoesRegisterRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        String image = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // 이미지 파일이 존재 할 경우
            // image = s3Uploader.upload(multipartFile, "static");
            // s3이미지 업로드
            log.info("이미지를 업로드 합니다.");
            image = s3Uploader.upload(multipartFile, "shoes");
            // s3이미지 업로드
        } else {
            image = "";
        }
        Shoes shoes = shoesRepository.save(requestDto.toEntity(image));
        return shoes.getId();
    }

    // 관리자 상품 삭제
    @Transactional
    public void deleteShoes(Long shoesId, Long userId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

        shoesRepository.delete(shoes);
    }

    // 신발(게시물) 상세 조회
    public ShoesResponseDto getShoesById(Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));


        return ShoesResponseDto.from(shoes);
    }

    //게시글 전체 출력 -> 무한 스크롤 -> total 페이지 개수 X
    // -> 다음 데이터가 있는지 알기 위해 처음에 데이터를 들고 올 때 limit+1까지 들고 옴
    // 해당 데이터가 있다는 것만 알려줌.
    // -> Repository로 옮겨야 할 듯..
    public ShoesPageResponse getShoesAll(Pageable pageable) {
        final Slice<Shoes> page = shoesRepository.getShoesAll(pageable);
        return ShoesPageResponse.from(page);
    }

    // 신발 카테고리별 조회
    public ShoesPageResponse getShoesByCategory(Pageable pageable, String brand) {
        //final Slice<Shoes> page = shoesRepository.getShoesByCategory(pageable, brand);
        final Slice<Shoes> page = shoesRepository.findAllByBrand(pageable, brand);
        return ShoesPageResponse.from(page);
    }

    // 게시물 업데이트
    @Transactional
    public ShoesResponseDto update(Long shoesId, ShoesUpdateRequestDto requestDto, MultipartFile multipartFile) throws IOException{
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
        shoes.update(requestDto.toEntity());

        String image = null;
        if (!multipartFile.isEmpty()) {                                                     // 사진이 수정된 경우
            image = (s3Uploader.upload(multipartFile, "shoes"));                   // 새로들어온 이미지 s3 저장
            Shoes shoe = shoesRepository.findById(shoesId).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 신발이 없습니다.")
            );
            if (!shoe.getImage().equals("")) {
                s3Uploader.delete(shoe.getImage(), "shoes");                          // 이전 이미지 파일 삭제
            }
            shoe.updateImage(image);
        }
        return ShoesResponseDto.from(shoes);
    }
}
