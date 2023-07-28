package de.flower.rmt.ui.page.event.manager.notification;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.test.TestData;
import de.flower.rmt.ui.markup.html.form.field.FormFieldPanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

import java.util.Collections;


public class RecipientListFormComponentlTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        Event event = new TestData().newEvent();
        final IModel model =  Model.of(Collections.emptyList());
        Component c = new NotificationPanel.RecipientListFormComponent(model, Model.of(event));
        wicketTester.startComponentInPage(c);
        wicketTester.dumpPage();
    }

    @Test
    public void testRenderFormFieldPanel() {
        Event event = new TestData().newEvent();
        final IModel model =  Model.of(Collections.emptyList());
        FormComponent c = new NotificationPanel.RecipientListFormComponent(model, Model.of(event));
        wicketTester.startComponentInPage(new FormFieldPanel("panel", c), Markup.of("<div wicket:id='panel' labelKey='button.add'/>"));
        wicketTester.dumpPage();
    }
}
