package somossuinos.aptcomplex.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
