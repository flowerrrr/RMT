package de.flower.rmt.ui.page.calendar;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.CalendarManager;
import de.flower.rmt.service.type.CalendarFilter;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class CalendarSelectPanel extends BasePanel<List<CalendarFilter>> {

    @SpringBean
    private CalendarManager calendarManager;

    public CalendarSelectPanel(final IModel<List<CalendarFilter>> model) {
        super(model);

        final Form form = new Form("form");
        add(form);

        final CheckBoxMultipleChoice<CalendarFilter> c = new CheckBoxMultipleChoice<>("calendars", model, getCalendarFilters());
        c.setChoiceRenderer(new IChoiceRenderer<CalendarFilter>() {

            @Override
            public Object getDisplayValue(final CalendarFilter object) {
                String s;
                s = new ResourceModel(CalendarFilter.Type.getResourceKey(object.type)).getObject();
                if (object.type == CalendarFilter.Type.TEAM) {
                    s += " " + object.team.getName();
                }
                return s;
            }

            @Override
            public String getIdValue(final CalendarFilter object, final int index) {
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

    private IModel<List<CalendarFilter>> getCalendarFilters() {
        return new LoadableDetachableModel<List<CalendarFilter>>() {
            @Override
            protected List<CalendarFilter> load() {
                return calendarManager.getCalendarFilters();
            }
        };
    }
}
