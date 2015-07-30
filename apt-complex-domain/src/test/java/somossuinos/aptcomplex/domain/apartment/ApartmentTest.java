package somossuinos.aptcomplex.domain.apartment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.domain.TestConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class ApartmentTest {

    @PersistenceContext
    private EntityManager em;

    @Before
    public void before() {
    }

    @Test
    public void testSave() {
        Apartment.Builder.get().withNumber("800");
    }

}
