package somossuinos.aptcomplex.domain.apartment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.domain.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class ApartmentServiceIT {

    @Test
    public void testGetByReferenceMonth() {
        // In a set of apartments, one may have fees others not.
        // All apartments should be returned, even without fees
        // for the month.
    }

}
