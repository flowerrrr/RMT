package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.type.Surface;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.springframework.transaction.TransactionStatus;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AbstractSoccerEventTest extends AbstractRMTIntegrationTests {

    /**
     * For for developing. Once implementation runs test is disabled
     */
    @Test(enabled = true)
    public void testEmbeddedSurfaceList() {
        TransactionStatus status = transactionManager.getTransaction(null);
        Match entity = new Match(testData.getClub());
        entity.getSurfaceList().add(Surface.NATURAL_GRASS);
        entity.getSurfaceList().add(Surface.ASH);
        em.persist(entity);
        transactionManager.commit(status);
        // see generated sql query
        entity = em.find(Match.class, entity.getId());
        // next line should not throw LazyInitException
        entity.getSurfaceList().get(0);
    }

}
