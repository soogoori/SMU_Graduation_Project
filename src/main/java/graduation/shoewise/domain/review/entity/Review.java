package graduation.shoewise.domain.review.entity;

import graduation.shoewise.domain.BaseEntity;
import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
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

    private static final int MINIMUM_RATING = 0;
    private static final int MAXIMUM_RATING = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User:Review = 일대다
    @ManyToOne(fetch = FetchType.LAZY) // 개파필
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_id", nullable = false)
    private Shoes shoes;

    @Lob
    private String content;

    private String image;

    @Column(nullable = false)
    private double rating;

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
    public void updateReview(ReviewUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.fit = requestDto.getFit();
        this.width = requestDto.getWidth();
        this.feeling = requestDto.getFeeling();
        this.rating = requestDto.getRating();
    }

    public boolean isWrittenBy(User user){
        return this.user.equals(user);
    }


}
