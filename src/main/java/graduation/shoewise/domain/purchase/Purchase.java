package graduation.shoewise.domain.purchase;

import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.purchase.dto.PurchaseRequestDto;
import graduation.shoewise.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int size;

    // 사이즈가 어떤지
    @Enumerated(EnumType.STRING)
    private Fit fit; // BIG, NORMAL, SMALL

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    public void update(PurchaseRequestDto requestDto) {
        this.name = requestDto.getName();
        this.size = requestDto.getSize();
        this.fit = requestDto.getFit();
    }
}
