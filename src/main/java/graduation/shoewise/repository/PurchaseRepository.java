package graduation.shoewise.repository;

import graduation.shoewise.entity.purchase.Purchase;
import graduation.shoewise.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    Optional<Purchase> findPurchaseByUser(User user);
}
