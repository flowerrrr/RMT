package de.flower.rmt.repository;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.hibernate.LazyInitializationException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

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

    @Test
    public void testFindAllFiltersClubs() {
        List<Team> list = teamRepo.findAll();
        log.info(list.toString());
        assertEquals(list.size(), 3);
    }
}
