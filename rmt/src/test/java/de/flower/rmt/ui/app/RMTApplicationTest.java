package de.flower.rmt.ui.app;

import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class RMTApplicationTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testResourceStrings() {
        Uniform u = new Uniform(null);
        u.setShirt("shirt");
        u.setShorts("shorts");
        u.setSocks("socks");
        assertEquals(new StringResourceModel("uniform.set", Model.of(u)).getObject(), "Hemd: shirt, Hose: shorts, Stutzen: socks");
    }
}
