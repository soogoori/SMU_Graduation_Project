package graduation.shoewise.repository;

import graduation.shoewise.entity.shoes.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {


}
