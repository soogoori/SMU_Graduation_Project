package graduation.shoewise.domain.shoes.service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.shoes.dto.ShoesRegisterRequestDto;
import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import graduation.shoewise.domain.shoes.dto.ShoesUpdateRequestDto;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static graduation.shoewise.domain.shoes.QShoes.shoes;


@Service
@RequiredArgsConstructor
public class ShoesService {

    private final ShoesRepository shoesRepository;
    private final ReviewRepository reviewRepository;
    private final JPAQueryFactory jpaQueryFactory;


    // 관리자 상품 등록
    @Transactional
    public Long registerShoes(ShoesRegisterRequestDto requestDto){
        Shoes shoes = shoesRepository.save(requestDto.toEntity());
        return shoes.getId();
    }

    // 관리자 상품 삭제
    @Transactional
    public void deleteShoes(Long shoesId) {
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
    public Slice<Shoes> getShoesAll(Pageable pageable) {
        JPAQuery<Long> shoesResponseList = jpaQueryFactory
                .select(shoes.id)
                .from(shoes)
                //.orderBy()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);

        List<Long> content = new ArrayList<>();
        content = shoesResponseList.fetch();

        boolean hasNext=false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext=true;
        }
        Slice<Long> slice = new SliceImpl<>(content, pageable, hasNext);


        JPAQuery<Shoes> jpaQuery = jpaQueryFactory.selectFrom(shoes)
                .where(shoes.id.in(slice.getContent()));

        return new SliceImpl<>(jpaQuery.fetch(), pageable, hasNext);

    }


    // 게시물 업데이트
    @Transactional
    public ShoesResponseDto update(Long shoesId, ShoesUpdateRequestDto requestDto) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
        shoes.update(requestDto.toEntity());

        return ShoesResponseDto.from(shoes);
    }

}
