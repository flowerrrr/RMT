package de.flower.common.ui.serialize;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.serialize.PageSerializationValidatorListener.PageSerializationException;
import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.dto.Password;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;


public class PageSerializationValidatorListenerTest extends AbstractRMTWicketIntegrationTests {

    @Test(expectedExceptions = {PageSerializationException.class })
    public void testPageSerializationValidatorDetectsEntity() {
        wicketTester.startComponentInPage(new TestPanel(Model.of(new Club("foobar"))));
    }

    @Test(expectedExceptions = {PageSerializationException.class })
    public void testPageSerializationValidatorDetectsEntityInSubpackage() {
        wicketTester.startComponentInPage(new TestPanel(Model.of(new Match(new Club("foobar")))));
    }

    @Test
    public void testPageSerializationValidator() {
        wicketTester.startComponentInPage(new TestPanel(Model.of(new Password(1L))));
    }

    public static class TestPanel extends BasePanel {

        public TestPanel(IModel<?> model) {
            super(model);
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel></wicket:panel>");
        }
    }

}
