package graduation.shoewise.domain.purchase.repository;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    Optional<Purchase> findPurchaseByUser(User user);
    Optional<Purchase> findPurchaseByUserNickname(String nickname);

    Page<Purchase> findAll(Pageable pageable);
    Page<Purchase> findAllByUserId(Long id, Pageable pageable);

    Optional<Purchase> findByIdAndNickname(Long id, String nickname);
}
