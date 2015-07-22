package somossuinos.aptcomplex.domain.apartment;

import java.util.List;
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
    public List<Apartment> getAll() {
        return repository.findAll();
    }

}
