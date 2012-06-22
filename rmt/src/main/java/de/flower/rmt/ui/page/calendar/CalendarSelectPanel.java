package de.flower.rmt.ui.page.calendar;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.service.ICalendarManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author flowerrrr
 */
public class CalendarSelectPanel extends BasePanel {

    @SpringBean
    private ICalendarManager calendarManager;

    public CalendarSelectPanel(final IModel<List<CalendarType>> model) {
        super(model);

        final Form form = new Form("form");
        add(form);

        final CheckBoxMultipleChoice c = new CheckBoxMultipleChoice("calendars", model, Arrays.asList(CalendarType.values()));
        c.setChoiceRenderer(new IChoiceRenderer<CalendarType>() {

            @Override
            public Object getDisplayValue(final CalendarType object) {
                return new ResourceModel(CalendarType.getResourceKey(object)).getObject();
            }

            @Override
            public String getIdValue(final CalendarType object, final int index) {
                return "" + index;
            }
        });
        c.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                // notify listeners about model change.
                AjaxEventSender.send(this.getComponent(), model);
            }
        });

        form.add(c);
    }

}
