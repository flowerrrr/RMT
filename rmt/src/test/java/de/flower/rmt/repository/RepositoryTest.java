package de.flower.rmt.repository;

import com.mysema.query.types.expr.BooleanExpression;
import de.flower.rmt.model.db.entity.QUser;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.hibernate.LazyInitializationException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;


public class RepositoryTest extends AbstractRMTIntegrationTests {

    @Test(expectedExceptions = {LazyInitializationException.class})
    public void testManyToOneLazyFetching() {
        User user = userRepo.findOne(1L);
        // this should trigger a LIE.
        user.getClub().getName();
    }

    @Test
    public void testEagerFetching() {
        BooleanExpression nr1 = QUser.user.id.eq(1L);
        User user = userRepo.findAll(nr1, QUser.user.club).get(0);
        log.info(user.getClub().getName());
    }

    @Test
    public void testFindAllFiltersClubs() {
        List<Team> list = teamRepo.findAll();
        log.info(list.toString());
        assertEquals(list.size(), 3);
    }

}
