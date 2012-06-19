package de.flower.rmt.model.db.type;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.joda.time.LocalTime;
import org.springframework.transaction.TransactionStatus;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class PersistentSplitDateTimeTypeTest extends AbstractRMTIntegrationTests {

    /**
     * For for developing. Once implementation runs test is disabled
     */
    @Test(enabled = true)
    public void testUserType() {
        TransactionStatus status = transactionManager.getTransaction(null);
        CalItem entity = new CalItem();
        entity.getStartDate().setDate(new Date());
        entity.getStartDate().setTime(new LocalTime());
        SplitDateTime startDate = entity.getStartDate();
        em.persist(entity);
        transactionManager.commit(status);
        log.info(entity.toString());
        // see generated sql query
        entity = em.find(CalItem.class, entity.getId());
        assertEquals(entity.getStartDate(), startDate);
    }
}
