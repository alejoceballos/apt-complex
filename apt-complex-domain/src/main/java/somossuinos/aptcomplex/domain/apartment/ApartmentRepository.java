package somossuinos.aptcomplex.domain.apartment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ceballos
 * @since 2015-07-19
 */
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("from Apartment a left outer join fetch a.residents")
    List<Apartment> findAllEager();
}
