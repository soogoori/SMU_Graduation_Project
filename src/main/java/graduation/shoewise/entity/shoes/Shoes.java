package graduation.shoewise.entity.shoes;

import graduation.shoewise.entity.BaseEntity;
import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shoes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    private String image;

    /*// 사이즈
    @Enumerated(EnumType.STRING)
    private Fit fit; // BIG, NORMAL, SMALL

    // 발볼
    @Enumerated(EnumType.STRING)
    private Width width; // WIDE, NORMAL, NARROW

    // 착화감
    @Enumerated(EnumType.STRING)
    private Feeling feeling; // GOOD, NORMAL, BAD*/

    @OneToMany(mappedBy = "shoes")
    private List<Review> review = new ArrayList<>();


}
