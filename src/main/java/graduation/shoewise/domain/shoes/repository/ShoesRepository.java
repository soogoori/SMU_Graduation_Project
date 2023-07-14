package graduation.shoewise.domain.shoes.repository;

import graduation.shoewise.domain.shoes.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoesRepository extends JpaRepository<Shoes, Long>, ShoesRepositoryCustom {

    List<Shoes> findAllByOrderByCreateDateDesc();

    Page<Shoes> findAllByOrderByCreateDateDesc(Pageable pageable);

    //Page<Shoes> findAllyByBrandOrderById(Pageable pageable);

    Slice<Shoes> findAllShoes(Pageable pageable); // 무한 스크롤

    @Query(value = "update Shoes s "
            + "set s.rating = (s.totalRating + :reviewRating) / cast((s.reviewCount + 1) as double), "
            + "s.reviewCount = s.reviewCount + 1, "
            + "s.totalRating = s.totalRating + :reviewRating "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewInsert(Long shoesId, int reviewRating);

    @Query(value = "update Shoes s "
            + "set s.rating = case s.reviewCount when 1 then 0 "
            + "else ((s.totalRating - :reviewRating) / cast((s.reviewCount - 1) as double)) end , "
            + "s.reviewCount = s.reviewCount - 1, "
            + "s.totalRating = s.totalRating - :reviewRating "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewDelete(Long shoesId, int reviewRating);


    @Query(value = "update Shoes s "
            + "set s.rating = (s.totalRating + :ratingGap) / cast(s.reviewCount as double), "
            + "s.totalRating = s.totalRating + :ratingGap "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewUpdate(Long shoesId, int ratingGap);



}
