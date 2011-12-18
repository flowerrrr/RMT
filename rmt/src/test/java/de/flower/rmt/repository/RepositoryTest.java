package de.flower.rmt.repository;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.hibernate.LazyInitializationException;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class RepositoryTest extends AbstractIntegrationTests {

    @Test(expectedExceptions = { LazyInitializationException.class })
    public void testManyToOneLazyFetching() {
        User user = em.find(User.class, 1L);
        // this should trigger a LIE.
        user.getClub().getName();
    }
}
