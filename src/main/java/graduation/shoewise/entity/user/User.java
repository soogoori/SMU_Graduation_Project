package graduation.shoewise.entity.user;

import graduation.shoewise.entity.BaseEntity;
import graduation.shoewise.entity.purchase.Purchase;
import graduation.shoewise.entity.enums.ProviderType;
import graduation.shoewise.entity.enums.RoleType;
import graduation.shoewise.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder // 빌더 패턴
@Entity
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 전체 필드를 가지고 있는 생성자
public class User extends BaseEntity {

    // 기본키 생성을 데이터베이스에게 위임하는 방식
    // → id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT 해서 기본키 생성
    @Id // 기본키 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String name;

    private String nickname;

    @Column(nullable = false)
    private String email;

    private String password;

    private int size; // 발 길이
    private int width; // 발볼 너비 길이

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role; // USER, ADMIN

    @Enumerated(EnumType.STRING)
    private ProviderType provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    private String providerId;  // oauth2를 이용할 경우 아이디값

    public User(String name, String password, String email, ProviderType provider, String providerId, RoleType role) {
        this.name =name;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    /**
     * 정적 팩토리 메소드
     */
    public void updateNickName(String nickname){
        this.nickname = nickname;
    }

    public void updateSize(int size, int width) {
        this.size=size;
        this.width=width;
    }

    public User update(String name, ProviderType provider) {
        this.name = name;
        this.provider = provider;

        return this;
    }
}
