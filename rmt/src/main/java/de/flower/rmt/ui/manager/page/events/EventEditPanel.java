package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.TimeSelect;
import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.form.*;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.component.TeamSelect;
import de.flower.rmt.ui.manager.component.VenueSelect;
import de.flower.rmt.ui.manager.page.response.ResponsePage;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * A bit different form other editing panels cause it is always
 * called with a domain object passed into the panel.
 *
 * @author flowerrrr
 */
public class EventEditPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    public EventEditPanel(IModel<Event> model) {
        super();
        Check.notNull(model.getObject());

        EntityForm<Event> form = new CancelableEntityForm<Event>("form", new EventModel(model.getObject())) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Event> form) {
                final Event event = form.getModelObject();
                eventManager.save(event);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Event.class), AjaxEvent.EntityUpdated(Event.class)));
                setResponsePage(new ResponsePage(event.getId()));
                onClose(target);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target) {
                onClose(target);
            }
        };
        add(form);

        form.add(new TeamSelect("team"));

        DateTextField dateField = DateTextField.forDateStyle("date", "S-");
        // dateField.add(new DatePicker());
        form.add(new FormFieldPanel("date", dateField));

        TimeSelect timeField = new TimeSelect("time");
        form.add(new FormFieldPanel("time", timeField));

        VenueSelect venueSelect = new VenueSelect("venue");
        form.add(venueSelect);

        // form.add(surface label)

        form.add(new TextFieldPanel("summary"));

        form.add(new TextAreaPanel("comment"));

        // form.add(participants)

    }


}
