package somossuinos.aptcomplex.domain.person;

import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.domain.TestConfig;

/**
 * @author ceballos
 * @since 2015-07-20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class PersonRepositoryTest {

    private static final String RESIDENT_NAME = "John DOe";
    
    @Inject
    private PersonRepository repository;

    @Before
    @Transactional
    public void setUp() throws Exception {
        final Iterable<Person> residents = repository.findAll();
        repository.delete(residents);
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        final Iterable<Person> residents = repository.findAll();
        repository.delete(residents);
    }

    @Test
    public void testSaveAndLoadNonTransactional() {
        final Person original = new Person(RESIDENT_NAME);
        final Person saved = repository.save(original);
        final Person loaded = repository.findOne(saved.getId());

        Assert.assertTrue(original == saved && saved != loaded);
    }

    @Test
    @Transactional
    public void testSaveAndLoadTransactional() {
        final Person original = new Person(RESIDENT_NAME);
        final Person saved = repository.save(original);
        final Person loaded = repository.findOne(saved.getId());

        Assert.assertTrue(original == saved && saved == loaded);
    }

    @Test
    public void testUpdateNonTransactional() {
        final Person original = new Person(RESIDENT_NAME);
        final Person saved = repository.save(original);

        final Person loaded = repository.findOne(saved.getId());

        Assert.assertTrue(original == saved && saved != loaded);
    }


}