package somossuinos.aptcomplex.domain.apartment;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author ceballos
 * @since 2015-07-19
 */
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("from Apartment a " +
            "left outer join fetch a.residents " +
            "left outer join fetch a.fees"
    )
    List<Apartment> findAllEager();

    @Query("select distinct a " +
            "from Apartment a " +
            "left outer join fetch a.fees f " +
            "left outer join fetch f.items i " +
            "left outer join fetch f.payments p " +
            "where f.referenceDate between :iniDate and :endDate "
    )
    List<Apartment> findByFeePeriod(
            @Param("iniDate") final DateTime iniDate,
            @Param("endDate") final DateTime endDate);

    @Query("select distinct a " +
            "from Apartment a " +
            "left outer join fetch a.fees f " +
            "left outer join fetch f.items i " +
            "where a.id = :id " +
            "and f.referenceDate between :iniDate and :endDate "

    )
    Apartment findOneByReferenceMonth(
            @Param("id") final Long id,
            @Param("iniDate") final DateTime iniDate,
            @Param("endDate") final DateTime endDate);
}
