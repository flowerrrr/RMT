package de.flower.rmt.ui.app;

import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class RMTApplicationTest extends AbstractRMTWicketIntegrationTests {

    @Autowired
    private MessageSourceAccessor messageSource;

    @Test
    public void testResourceStrings() {
        Uniform u = new Uniform(null);
        u.setShirt("shirt");
        u.setShorts("shorts");
        u.setSocks("socks");
        String expected = "Hemd: shirt, Hose: shorts, Stutzen: socks";
        // test with spring message source
        final Object[] params = new Object[]{u.getShirt(), u.getShorts(), u.getSocks()};
        assertEquals(messageSource.getMessage("uniform.set", params), expected);
    }
}
