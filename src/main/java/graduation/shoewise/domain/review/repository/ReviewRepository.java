package graduation.shoewise.domain.review.repository;

import graduation.shoewise.domain.review.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserIdAndShoes(Long userId, Shoes shoes);
    Optional<Review> findByShoes(Shoes shoes);
    Optional<Review> findByUser(User user);


    Long countByShoesId(Long id);

    Page<Review> findAllByUserId(Long id, Pageable pageable);

    Page<Review> findAllByShoesId(Long shoesId, Pageable pageable);





}
