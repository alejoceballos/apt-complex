package somossuinos.aptcomplex.domain.utils;

import org.springframework.stereotype.Component;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.DataFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Set;

@Component
public class ITUtils {

    @Inject
    private EntityManagerFactory emf;

    public EntityManager em() {
        return emf.createEntityManager();
    }

    public EntityManager beginTx() {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    public void commitTx(final EntityManager em) {
        em.getTransaction().commit();
        em.close();
    }

    public void clearDatabase() {
        final EntityManager em = beginTx();

        final List<MonthlyBalance> balances = em.createQuery("from MonthlyBalance").getResultList();

        for (final MonthlyBalance balance : balances) {
            em.remove(balance);
        }

        final List<Apartment> apartments = em.createQuery("from Apartment").getResultList();

        for (final Apartment apartment : apartments) {
            em.remove(apartment);
        }

        commitTx(em);
    }

    public void populateDatabase() {
        EntityManager em = beginTx();

        final Set<Apartment> apartments = DataFactory.apartments();

        for (final Apartment apartment : apartments) {
            em.persist(apartment);
        }

        commitTx(em);
        em = beginTx();

        final Set<MonthlyBalance> balances = DataFactory.monthlyBalances(apartments);

        for (final MonthlyBalance balance : balances) {
            em.persist(balance);
        }

        commitTx(em);
    }

}
