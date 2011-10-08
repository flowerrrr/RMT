package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventsPage() {

        // fade in panel to preselect event-type before opening the event edit page.
        final PreCreateEventEditPanel preCreateEventEditPanel = new PreCreateEventEditPanel("selectEventType") {
            @Override
            public void onSelect(EventType eventType, AjaxRequestTarget target) {
                Event event = eventManager.newInstance(eventType);
                setResponsePage(new EventsEditPage(event));
            }
        };
        preCreateEventEditPanel.setOutputMarkupPlaceholderTag(true);
        preCreateEventEditPanel.setVisible(false);
        preCreateEventEditPanel.setOutputMarkupId(true);
        add(preCreateEventEditPanel);

        final AjaxLink newButton = new MyAjaxLink("newButton") {
           @Override
            public void onClick(AjaxRequestTarget target) {
               preCreateEventEditPanel.setVisible(true);
               target.add(preCreateEventEditPanel);
           }
        };
        add(newButton);


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
            protected void populateItem(final ListItem<Event> item) {
                final Event event = item.getModelObject();
                item.add(DateLabel.forDateStyle("date", Model.of(event.getDate()), "S-"));
                item.add(DateLabel.forDateStyle("time", Model.of(event.getTime().toDateTimeToday().toDate()), "-S"));
                item.add(new Label("type", new ResourceModel(EventType.from(event).getResourceKey())));
                item.add(new Label("team", event.getTeam().getName()));
                item.add(new Label("summary", event.getSummary()));
                item.add(new Label("responses", getResponses(event)));
                item.add(new Link("editButton") {

                    @Override
                    public void onClick() {
                        setResponsePage(new EventsEditPage(item.getModelObject()));
                    }
                });
                item.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.events.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        eventManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Event.class)));
                    }
                });
            }

            // TODO (flowerrrr - 11.09.11) use 'Converter'
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
