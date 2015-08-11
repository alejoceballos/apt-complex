package somossuinos.aptcomplex.domain.balance;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import somossuinos.aptcomplex.TestConfig;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.utils.ITUtils;
import somossuinos.aptcomplex.DataFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class MonthlyBalanceIT {

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

    private Set<Apartment> createApartments() {
        final EntityManager em = utils.beginTx();
        final Set<Apartment> apartmentsToSave = DataFactory.apartments();
        for (final Apartment apartment : apartmentsToSave) {
            em.persist(apartment);
        }
        utils.commitTx(em);

        return apartmentsToSave;
    }

    @Test
    public void testSaveMonthlyBalancesAllInTheSameTransaction() {
        EntityManager em = utils.beginTx();

        final Set<Apartment> apartmentsToSave = DataFactory.apartments();

        for (final Apartment apartment : apartmentsToSave) {
            em.persist(apartment);
        }

        final List<Apartment> savedApartments = em.createQuery("from Apartment").getResultList();

        final Set<MonthlyBalance> balancesToSave = DataFactory.monthlyBalances(savedApartments);

        for (final MonthlyBalance balance : balancesToSave) {
            em.persist(balance);
        }

        utils.commitTx(em);

        em = utils.em();
        final List<MonthlyBalance> savedBalances = em.createQuery("from MonthlyBalance").getResultList();
        em.close();

        Assert.assertThat(savedBalances.size(), CoreMatchers.is(balancesToSave.size()));
    }

    @Test
    public void testSaveMonthlyBalancesInDifferentTransactions() {
        EntityManager em = utils.beginTx();

        final Set<Apartment> apartmentsToSave = DataFactory.apartments();

        for (final Apartment apartment : apartmentsToSave) {
            em.persist(apartment);
        }

        utils.commitTx(em);
        em = utils.beginTx();

        final Set<MonthlyBalance> balancesToSave = DataFactory.monthlyBalances(apartmentsToSave);

        for (final MonthlyBalance balance : balancesToSave) {
            em.persist(balance);
        }

        utils.commitTx(em);

        em = utils.em();
        final List<MonthlyBalance> savedBalances = em.createQuery("from MonthlyBalance").getResultList();
        em.close();

        Assert.assertThat(savedBalances.size(), CoreMatchers.is(balancesToSave.size()));
    }

}