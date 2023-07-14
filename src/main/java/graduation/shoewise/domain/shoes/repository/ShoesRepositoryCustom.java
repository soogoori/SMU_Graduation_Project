package graduation.shoewise.domain.shoes.repository;

import graduation.shoewise.domain.shoes.Shoes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ShoesRepositoryCustom {

    Slice<Shoes> getShoesAll(Pageable pageable);
}
