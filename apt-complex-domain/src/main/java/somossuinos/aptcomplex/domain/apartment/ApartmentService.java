package somossuinos.aptcomplex.domain.apartment;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author ceballos
 * @since 2015-07-21
 */
@Named
public class ApartmentService {

    @Inject
    private ApartmentRepository repository;

    @Transactional
    public Apartment get(final long id) {
        final Apartment apartment = repository.findOne(1L);
        apartment.getResidents().size();
        return apartment;
    }

}
