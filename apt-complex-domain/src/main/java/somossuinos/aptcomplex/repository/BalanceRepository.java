package somossuinos.aptcomplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import somossuinos.aptcomplex.domain.balance.BalanceType;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.domain.balance.ReferenceMonth;

/**
 * Created by ceballos on 07/08/15.
 */
public interface BalanceRepository extends JpaRepository<MonthlyBalance, Long> {

    @Query("from MonthlyBalance b " +
            "left outer join fetch b.balanceGroups bg " +
            "left outer join fetch bg.apartmentsBalance ab " +
            "left outer join fetch ab.fees f " +
            "left outer join fetch f.items i " +
            "left outer join fetch f.payments p " +
            "join fetch ab.apartment a " +
            "where b.referenceMonth = :month " +
            "and bg.type = 'INCOMES' ")
    MonthlyBalance findWithApartmentsAndFeesByReferenceMonth(
            @Param("month") final ReferenceMonth month);

}
