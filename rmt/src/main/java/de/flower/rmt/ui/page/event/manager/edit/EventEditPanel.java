package de.flower.rmt.ui.page.event.manager.edit;

import de.flower.common.ui.js.JQuery;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.markup.html.form.*;
import de.flower.rmt.ui.markup.html.form.field.*;
import de.flower.rmt.ui.model.ModelFactory;
import de.flower.rmt.ui.model.TeamModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
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

    private UniformDropDownChoicePanel uniformDDCPanel;

    public EventEditPanel(final IModel<Event> model) {
        this(null, model);
    }

    public EventEditPanel(String id, final IModel<Event> m) {
        super(id, m);
        final IModel<Event> model = ModelFactory.eventModelWithAllAssociations(m.getObject());

        final EntityForm<Event> form = new CancelableEntityForm<Event>("form", model, createCancelLink(model)) {

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
                    target.appendJavaScript(JQuery.scrollToTop("slow"));
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

            @Override
            protected void onChange(AjaxRequestTarget target) {
                // reset uniform select box
                uniformDDCPanel.detach();
                uniformDDCPanel.setTeamModel(new TeamModel(form.getModelObject().getTeam()));
                target.add(uniformDDCPanel);
            }
        });

        form.add(new DateFieldPanel("date"));

        form.add(new DropDownChoicePanel("time", new TimeDropDownChoice("input")));

        form.add(new DropDownChoicePanel("kickoff", new TimeDropDownChoice("input")) {
            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }
        });

        form.add(new OpponentDropDownChoicePanel("opponent") {
            @Override
            public boolean isVisible() {
                return EventType.isMatch(model.getObject());
            }
        });

        form.add(new VenueDropDownChoicePanel("venue"));

        form.add(uniformDDCPanel = new UniformDropDownChoicePanel("uniform", new TeamModel.NullableTeamModel(model.getObject().getTeam())) {
            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }
        });

        form.add(new CheckBoxMultipleChoicePanel("surfaceList", new SurfaceCheckBoxMultipleChoice("input")) {
            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }

            @Override
            protected boolean isInstantValidationEnabled() {
                return false; // very annoying to have instant validation on multiselect boxes
            }
        });

        form.add(new TextFieldPanel("summary"));

        form.add(new TextAreaPanel("comment"));
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
