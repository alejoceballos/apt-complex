package somossuinos.aptcomplex.domain.apartment;

import com.google.common.collect.Iterables;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.domain.TestConfig;
import somossuinos.aptcomplex.domain.person.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class ApartmentRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    private ApartmentRepository repository;

    @Before
    public void setUp() throws Exception {
        final Iterable<Apartment> apts = repository.findAllEager();

        for (final Apartment apt : apts) {
            apt.getResidents().clear();
        }

        repository.save(apts);
    }

    @After
    public void tearDown() throws Exception {
        final Iterable<Apartment> apts = repository.findAllEager();

        for (final Apartment apt : apts) {
            apt.getResidents().clear();
        }

        repository.save(apts);
    }

    @Test
    public void testAmountIs36() throws Exception {
        final long size = repository.count();
        Assert.assertThat(size, CoreMatchers.is(36L));
    }

    @Test
    public void testFindAllWithGetIdInRange() throws Exception {
        final Iterable<Apartment> apts = repository.findAll();

        for (final Apartment apt : apts) {
            if (apt.getId() < 1 || apt.getId() > 36) {
                Assert.fail("Id out of range");
            }
        }

        Assert.assertTrue("All ids are inside range", true);
    }

    @Test
    public void testFindAllWithGetNumberInRange() throws Exception {
        final Iterable<Apartment> apts = repository.findAll();

        for (final Apartment apt : apts) {
            int number = Integer.valueOf(apt.getNumber());
            if (number < 101 || number > 1203) {
                Assert.fail("Number out of range");
            }
        }

        Assert.assertTrue("All numbers are inside range", true);
    }

    @Test
    public void testFindAllWithNoResidentsOutsideTransaction() {
        final Iterable<Apartment> apts = repository.findAll();
        final Set<Person> residents = apts.iterator().next().getResidents();

        thrown.expect(LazyInitializationException.class);

        residents.size();
    }

    @Test
    @Transactional
    public void testFindAllWithNoResidentsInsideTransaction() {
        final Iterable<Apartment> apts = repository.findAll();

        for (final Apartment apt : apts) {
            if (apt.getResidents() == null) {
                Assert.fail("Residents should be a persistent set");

            } else if (apt.getResidents().size() > 0) {
                Assert.fail("Residents data source should be empty");
            }
        }

        Assert.assertTrue("Residents data source is empty", true);
    }

    @Test
    public void testSaveAllWithTwoResidentsEachOutsideTransaction() {
        final Iterable<Apartment> originalApts = repository.findAllEager();

        int count = 0;
        for (final Apartment apt : originalApts) {
            apt.getResidents().add(new Person(String.format("John %s_1 Doe", ++count)));
            apt.getResidents().add(new Person(String.format("John %s_2 Doe", ++count)));
        }

        final Iterable<Apartment> savedApts = repository.save(originalApts);

        final Iterable<Apartment> loadedApts = repository.findAllEager();

        for (final Apartment apt : loadedApts) {
            if (apt.getResidents().size() != 2) {
                Assert.fail("Each apartment should have 2 residents saved");
            }

            if (Iterables.contains(originalApts, apt) || Iterables.contains(savedApts, apt)) {
                Assert.fail("Object instances should be different between different transactions");
            }
        }

        Assert.assertTrue("All apartments have 2 residents", true);
    }

    @Test
    @Transactional
    public void testSaveAllWithTwoResidentsEachInsideTransaction() {
        final Iterable<Apartment> originalApts = repository.findAll();

        int count = 0;
        for (final Apartment apt : originalApts) {
            apt.getResidents().add(new Person(String.format("John %s_1 Doe", ++count)));
            apt.getResidents().add(new Person(String.format("John %s_2 Doe", ++count)));
        }

        final Iterable<Apartment> savedApts = repository.save(originalApts);

        final Iterable<Apartment> loadedApts = repository.findAll();

        for (final Apartment apt : loadedApts) {
            if (apt.getResidents().size() != 2) {
                Assert.fail("Each apartment should have 2 residents saved");
            }

            if (!Iterables.contains(originalApts, apt) && !Iterables.contains(savedApts, apt)) {
                Assert.fail("Object instances should be the same within the same transaction");
            }
        }

        Assert.assertTrue("All apartments have 2 residents", true);
    }

}