package graduation.shoewise.domain.purchase.repository;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    Optional<Purchase> findPurchaseByUser(User user);
    Optional<Purchase> findPurchaseByUserName(String nickname);

    Slice<Purchase> findAllByUserId(Long id, Pageable pageable);

    @Query("select p from Purchase p where p.user.id = :userId and p.id = :id")
    Optional<Purchase> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
