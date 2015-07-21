package somossuinos.aptcomplex.domain.apartment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ceballos
 * @since 2015-07-19
 */
public interface ApartmentRepository extends CrudRepository<Apartment, Long> {

    @Query("from Apartment a left outer join fetch a.residents")
    Iterable<Apartment> findAllEager();
}
