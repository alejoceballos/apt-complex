package somossuinos.aptcomplex.domain.apartment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ceballos
 * @since 2015-07-19
 */
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

//    @Query("select distinct a " +
//            "from Apartment a " +
//            "left outer join fetch a.fees f " +
//            "left outer join fetch f.items i " +
//            "left outer join fetch f.payments p " +
//            "where f.referenceDate between :iniDate and :endDate ")
//    List<Apartment> findByFeePeriod(
//            @Param("iniDate") final DateTime iniDate,
//            @Param("endDate") final DateTime endDate);
//
//    @Query("select distinct a " +
//            "from Apartment a " +
//            "left outer join fetch a.fees f " +
//            "left outer join fetch f.items i " +
//            "left outer join fetch f.payments p " +
//            "where a.id = :id " +
//            "and f.referenceDate between :iniDate and :endDate ")
//    Apartment findOneByFeePeriod(
//            @Param("id") final Long id,
//            @Param("iniDate") final DateTime iniDate,
//            @Param("endDate") final DateTime endDate);
}
