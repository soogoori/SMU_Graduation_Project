package graduation.shoewise.domain.shoes;

import graduation.shoewise.domain.BaseEntity;
import graduation.shoewise.domain.enums.Feeling;
import graduation.shoewise.domain.enums.Fit;
import graduation.shoewise.domain.enums.Width;
import graduation.shoewise.domain.review.Review;
import graduation.shoewise.domain.shoes.dto.ShoesRequestDto;
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

    private int reviewCount;

    private int totalRating;

    @Column(name = "avg_rating", nullable = false)
    private double rating;

   /* @OneToMany(mappedBy = "shoes")
    private List<Review> review = new ArrayList<>();*/

    /*// 사이즈
    @Enumerated(EnumType.STRING)
    private Fit fit; // BIG, NORMAL, SMALL

    // 발볼
    @Enumerated(EnumType.STRING)
    private Width width; // WIDE, NORMAL, NARROW

    // 착화감
    @Enumerated(EnumType.STRING)
    private Feeling feeling; // GOOD, NORMAL, BAD*/

    public void update(Shoes updateShoes){
        updateName(updateShoes.getName());
        updateImage(updateShoes.getImage());
    }

    public void updateName(String name) {
        if (name != null) {
            this.name=name;
        }
    }
    public void updateImage(String image) {
        if (image != null) {
            this.image = image;
        }
    }

}
