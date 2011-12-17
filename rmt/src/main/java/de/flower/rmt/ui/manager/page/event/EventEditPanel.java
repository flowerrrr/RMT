package de.flower.rmt.ui.manager.page.event;

import de.flower.common.ui.form.TimeDropDownChoice;
import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.form.CancelableEntityForm;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.DateFieldPanel;
import de.flower.rmt.ui.common.form.field.DropDownChoicePanel;
import de.flower.rmt.ui.common.form.field.TextAreaPanel;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.component.TeamDropDownChoicePanel;
import de.flower.rmt.ui.manager.component.VenueDropDownChoicePanel;
import de.flower.rmt.ui.manager.page.invitations.InvitationsPage;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
        Check.notNull(model.getObject());

        EntityForm<Event> form = new CancelableEntityForm<Event>("form", getEventModel(model)) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Event> form) {
                final Event event = form.getModelObject();
                if (event.isNew()) {
                    eventManager.create(event, true);
                } else {
                    eventManager.save(event);
                }
                // target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Event.class), AjaxEvent.EntityUpdated(Event.class)));
                setResponsePage(new InvitationsPage(form.getModel()));
                // onClose(target);
            }
        };
        add(form);

        form.add(new TeamDropDownChoicePanel("team"));
        form.add(new DateFieldPanel("date"));
        form.add(new DropDownChoicePanel("time", new TimeDropDownChoice("input")));
        form.add(new VenueDropDownChoicePanel("venue"));

        // form.add(surface label)

        form.add(new TextFieldPanel("summary"));
        form.add(new TextAreaPanel("comment"));

        // form.add(participants)

    }

    /**
     * Return event instance initialized with team and venue association.
     */
    IModel<Event> getEventModel(final IModel<Event> model) {
        return new EventModel(model.getObject().getId(), Event_.team, Event_.venue);
    }
}
