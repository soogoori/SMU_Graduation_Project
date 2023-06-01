package graduation.shoewise.repository;

import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.shoes.Shoes;
import graduation.shoewise.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMySize(User user);

    Optional<Review> findByUser(User user);

    Optional<Review> findByShoes(Shoes shoes);
}
