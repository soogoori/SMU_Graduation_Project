package graduation.shoewise.domain.user.repository;


import graduation.shoewise.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    User findByProviderId(String providerId);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsById(Long memberId);


    /**
     * 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드
     * 정보 제공을 동의한 순간 DB에 저장해야하지만, 아직 추가 정보(선호장르, 닉네임 등)를 입력받지 않았으므로
     * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태이다.
     * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
     */
    //Optional<User> getRefreshTokenById(String provider, String providerId);

}
