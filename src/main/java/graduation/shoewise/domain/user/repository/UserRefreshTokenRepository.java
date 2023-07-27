package graduation.shoewise.domain.user.repository;

import graduation.shoewise.domain.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(Long userId);
    UserRefreshToken findByUserIdAndRefreshToken(Long userId, String refreshToken);

}
