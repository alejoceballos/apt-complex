package somossuinos.aptcomplex.domain.utils;

import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.apartment.ApartmentRepository;
import somossuinos.aptcomplex.domain.person.PersonRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ApartmentTestUtils {

    @PersistenceContext
    private EntityManager em;

    public void wipeDependenciesOut() {
        em.createQuery("delete from Apartment");
    }

}
