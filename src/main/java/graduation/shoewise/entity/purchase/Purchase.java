package graduation.shoewise.entity.purchase;

import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.user.User;
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

    @Column(columnDefinition = "TEXT")
    private String review;

    public void update(String name, int size, String review) {
        this.name = name;
        this.size=size;
        this.review = review;
    }
}
