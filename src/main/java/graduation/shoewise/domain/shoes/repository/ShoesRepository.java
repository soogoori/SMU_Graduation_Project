package graduation.shoewise.domain.shoes.repository;

import graduation.shoewise.domain.shoes.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoesRepository extends JpaRepository<Shoes, Long>, ShoesRepositoryCustom {

    List<Shoes> findAllByOrderByCreateDateDesc();

    @Modifying
    @Query(value = "update Shoes s "
            + "set s.avgRating = (s.totalRating + :reviewRating) / cast((s.reviewCount + 1) as double), "
            + "s.reviewCount = s.reviewCount + 1, "
            + "s.totalRating = s.totalRating + :reviewRating "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewInsert(@Param("shoesId") Long shoesId, @Param("reviewRating") double reviewRating);

    @Modifying
    @Query(value = "update Shoes s "
            + "set s.avgRating = case s.reviewCount when 1 then 0 "
            + "else ((s.totalRating - :reviewRating) / cast((s.reviewCount - 1) as double)) end , "
            + "s.reviewCount = s.reviewCount - 1, "
            + "s.totalRating = s.totalRating - :reviewRating "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewDelete(@Param("shoesId") Long shoesId, @Param("reviewRating") double reviewRating);


    @Modifying
    @Query(value = "update Shoes s "
            + "set s.avgRating = (s.totalRating + :ratingGap) / cast(s.reviewCount as double), "
            + "s.totalRating = s.totalRating + :ratingGap "
            + "where s.id = :shoesId")
    void updateShoesStatisticsForReviewUpdate(@Param("shoesId")Long shoesId, @Param("ratingGap") double ratingGap);



}
