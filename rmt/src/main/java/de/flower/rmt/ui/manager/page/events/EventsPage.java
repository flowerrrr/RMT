package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.FeatureNotImplementedLink;
import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author oblume
 */
public class EventsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventsPage() {

        final Link addButton = new FeatureNotImplementedLink("newButton", "New Event");
        add(addButton);

        final IModel<List<Event>> listModel = getListModel();
        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);
        listContainer.add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        listContainer.add(new ListView<Event>("list", listModel) {
            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(ListItem<Event> item) {
                final Event event = item.getModelObject();
                item.add(new Label("date", formatDate(event.getDate())));
                item.add(new Label("type", new ResourceModel("manager.event.type." + event.getType())));
                item.add(new Label("team", event.getTeam().getName()));
                item.add(new Label("summary", event.getSummary()));
                item.add(new Label("responses", getResponses(event)));
                item.add(new FeatureNotImplementedLink("editButton", "Editing Event"));
                item.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.events.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        eventManager.delete(event);
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Event.class)));
                    }
                });
            }

            // TODO (oblume - 11.09.11) use 'Converter'
            private String formatDate(DateTime date) {
                return "not implemented";
            }

            private String getResponses(Event event) {
                return "Zusagen / Absagen / offen";
            }
        });
        listContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Event.class)));
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll();
            }
        };
    }
}
