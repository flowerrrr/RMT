package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.TimeDropDownChoice;
import de.flower.rmt.ui.markup.html.form.field.DateFieldPanel;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class CalItemEditPanel extends BasePanel<CalItem> {

    @SpringBean
    private ICalendarManager calendarManager;

    public CalItemEditPanel(final IModel<CalItem> model) {
        super(model);
        Check.notNull(model);

        add(new AjaxEventListener(CalItem.class));

        add(new Label("heading", new StringResourceModel("calendar.editpanel.new.${new}.heading", model)));

        EntityForm<CalItem> form = new EntityForm<CalItem>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<CalItem> form) {
                calendarManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, CalItem.class);
                onClose(target);
            }
        };
        add(form);

        form.add(new DateFieldPanel("startDate.date"));

        form.add(new DropDownChoicePanel("startDate.time", new TimeDropDownChoice("input")));

        form.add(new DateFieldPanel("endDate.date"));

        form.add(new DropDownChoicePanel("endDate.time", new TimeDropDownChoice("input")));

        form.add(new AjaxLink("cancelButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onClose(target);
            }
        });
    }
}
