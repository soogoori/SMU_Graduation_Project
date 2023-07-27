package graduation.shoewise.domain.shoes;

import graduation.shoewise.domain.BaseEntity;
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
public class Shoes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    private String image;

    private int reviewCount;

    private double totalRating;

    @Column(name = "avg_rating", nullable = false)
    private double avgRating;

    // 사이즈
    private double fitPercent; // BIG, NORMAL, SMALL

    // 발볼
    private double widthPercent; // WIDE, NORMAL, NARROW

    // 착화감
    private double feelingPercent; // GOOD, NORMAL, BAD

    public void update(Shoes updateShoes){
        updateName(updateShoes.getName());
        updateBrand(updateShoes.getBrand());
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

    public void updateBrand(String brand){
        if(brand!=null){
            this.brand = brand;
        }
    }

}
