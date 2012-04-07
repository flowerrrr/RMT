package de.flower.rmt.model.event;

import de.flower.rmt.model.Surface;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.hibernate.LazyInitializationException;
import org.springframework.transaction.TransactionStatus;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AbstractSoccerEventTest extends AbstractIntegrationTests {

    /**
     * For for developing. Once implementation runs test is disabled
     */
    @Test(expectedExceptions = LazyInitializationException.class, enabled = false)
    public void testEmbeddedSurfaceList() {
        TransactionStatus status = transactionManager.getTransaction(null);
        Match entity = new Match(testData.getClub());
        entity.getSurfaceList().add(Surface.NATURAL_GRASS);
        entity.getSurfaceList().add(Surface.ASH);
        em.persist(entity);
        transactionManager.commit(status);
        // see generated sql query
        entity = em.find(Match.class, entity.getId());
        // next line throw LazyInitException
        entity.getSurfaceList().get(0);
    }

}
