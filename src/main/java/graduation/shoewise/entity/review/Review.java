package graduation.shoewise.entity.review;

import graduation.shoewise.entity.BaseEntity;
import graduation.shoewise.entity.shoes.Shoes;
import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import graduation.shoewise.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User:Review = 일대다
    @ManyToOne(fetch = FetchType.LAZY) // 개파필
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shoes shoes;

    @Lob
    private String content;

    @Column(nullable = false)
    private Integer rating;

    // 사이즈
    @Enumerated(EnumType.STRING)
    private Fit fit; // BIG, NORMAL, SMALL

    // 발볼
    @Enumerated(EnumType.STRING)
    private Width width; // WIDE, NORMAL, NARROW

    // 착화감
    @Enumerated(EnumType.STRING)
    private Feeling feeling; // GOOD, NORMAL, BAD


    /**
     * 정적 팩토리 메소드
     */
    public void update(String content) {
        this.content = content;
    }
}
