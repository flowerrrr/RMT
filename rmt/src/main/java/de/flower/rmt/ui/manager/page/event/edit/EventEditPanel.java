package de.flower.rmt.ui.manager.page.event.edit;

import de.flower.common.ui.form.TimeDropDownChoice;
import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.common.form.CancelableEntityForm;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.*;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.component.OpponentDropDownChoicePanel;
import de.flower.rmt.ui.manager.component.SurfaceDropDownChoice;
import de.flower.rmt.ui.manager.component.TeamDropDownChoicePanel;
import de.flower.rmt.ui.manager.component.VenueDropDownChoicePanel;
import de.flower.rmt.ui.manager.page.event.EventPage;
import de.flower.rmt.ui.manager.page.event.EventTabPanel;
import de.flower.rmt.ui.model.ModelFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * A bit different form other editing panels cause it is always
 * called with a domain object passed into the panel.
 *
 * @author flowerrrr
 */
public class EventEditPanel extends BasePanel<Event> {

    @SpringBean
    private IEventManager eventManager;

    public EventEditPanel(final IModel<Event> model) {
        this(null, model);
    }

    public EventEditPanel(String id, final IModel<Event> model) {
        super(id, model);
        Check.notNull(model.getObject());

        EntityForm<Event> form = new CancelableEntityForm<Event>("form",
                ModelFactory.eventModelWithAllAssociations(model.getObject()),
                createCancelLink(model)) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Event> form) {
                final Event event = form.getModelObject();
                if (event.isNew()) {
                    eventManager.create(event, true);
                    // jump to email tab
                    setResponsePage(EventPage.class, EventPage.getPageParams(form.getModelObject().getId(), EventTabPanel.NOTIFICATION_PANEL_INDEX));
                } else {
                    eventManager.save(event);
                    // stay on page
                }
            }
        };
        add(form);

        form.add(new TextFieldPanel("type", new TextField(AbstractFormFieldPanel.ID, new ResourceModel(EventType.from(model.getObject()).getResourceKey())))
                .setEnabled(false));
        form.add(new TeamDropDownChoicePanel("team") {
            @Override
            public boolean isEnabled() {
                return model.getObject().isNew();
            }
        });
        form.add(new DateFieldPanel("date"));
        form.add(new DropDownChoicePanel("time", new TimeDropDownChoice("input")));
        form.add(new OpponentDropDownChoicePanel("opponent") {
            @Override
            public boolean isVisible() {
                return EventType.isMatch(model.getObject());
            }
        });
        form.add(new VenueDropDownChoicePanel("venue"));

        form.add(new DropDownChoicePanel("surface", new SurfaceDropDownChoice("input")) {
            @Override
            public boolean isVisible() {
                return EventType.hasSurface(model.getObject());
            }
        });

        form.add(new TextFieldPanel("summary"));
        form.add(new TextAreaPanel("comment"));

        // form.add(participants)
    }

    /**
     * Visibility of cancel button depends on persisted state of entity
     *
     * @param model
     * @return
     */
    private AbstractLink createCancelLink(final IModel<Event> model) {
        return new Links.HistoryBackLink("cancelButton") {
            @Override
            public boolean isVisible() {
                return model.getObject().isNew();
            }
        };
    }
}
