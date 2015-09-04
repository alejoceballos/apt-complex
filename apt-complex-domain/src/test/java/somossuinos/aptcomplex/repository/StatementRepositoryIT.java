package somossuinos.aptcomplex.repository;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.TestConfig;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;
import somossuinos.aptcomplex.domain.utils.ITUtils;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class StatementRepositoryIT {

    @Inject
    private ITUtils utils;

    @Inject
    private StatementRepository repository;

    @Before
    public void setUp() {
        utils.clearDatabase();
    }

    @After
    public void tearDown() {
        utils.clearDatabase();
    }

    @Test
    public void testFindByReferenceMonth() throws Exception {
        utils.populateDatabase();

        final MonthlyStatement statement = repository.findWithApartmentsByReferenceMonth(
                new ReferenceMonth((short) 2015, (short) 6));

        Assert.assertThat(statement, CoreMatchers.notNullValue());
    }
}