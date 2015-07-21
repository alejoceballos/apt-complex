package somossuinos.aptcomplex.domain.person;

import org.springframework.data.repository.CrudRepository;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
}
