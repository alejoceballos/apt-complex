package somossuinos.aptcomplex.domain.apartment;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.TestConfig;
import somossuinos.aptcomplex.domain.person.Person;
import somossuinos.aptcomplex.domain.utils.ITUtils;
import somossuinos.aptcomplex.DataFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class ApartmentIT {

    @Inject
    private ITUtils utils;

    @Before
    public void setUp() {
        utils.clearDatabase();
    }

    @After
    public void tearDown() {
        utils.clearDatabase();
    }

    @Test
    public void testSaveApartments() {
        EntityManager em = utils.beginTx();

        final Set<Apartment> apartmentsToSave = DataFactory.apartments();

        for (final Apartment apartment : apartmentsToSave) {
            em.persist(apartment);
        }

        utils.commitTx(em);

        em = utils.em();
        final List<Apartment> savedApartments = em.createQuery("from Apartment").getResultList();
        em.close();

        Assert.assertThat(savedApartments.size(), CoreMatchers.is(apartmentsToSave.size()));
    }

    @Test
    public void testSavePersonsThroughApartments() {
        EntityManager em = utils.beginTx();

        final Set<Apartment> apartmentsToSave = DataFactory.apartments();

        for (final Apartment apartment : apartmentsToSave) {
            em.persist(apartment);
        }

        utils.commitTx(em);

        em = utils.em();
        final List<Person> savedPeople = em.createQuery("from Person").getResultList();
        em.close();

        Assert.assertThat(savedPeople.size(), CoreMatchers.is(apartmentsToSave.size()));
    }

}
