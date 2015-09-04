package somossuinos.aptcomplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;

/**
 * Created by ceballos on 02/09/15.
 */
public interface StatementRepository extends JpaRepository<MonthlyStatement, Long> {

    @Query("from MonthlyStatement ms " +
            "left outer join fetch ms.statementGroups sg " +
            "left outer join fetch sg.apartmentsStatement aps " +
            "join fetch aps.apartment a " +
            "where ms.referenceMonth = :month " +
            "and sg.type = 'INCOMES' ")
    MonthlyStatement findWithApartmentsByReferenceMonth(
            @Param("month") final ReferenceMonth month
    );

}
