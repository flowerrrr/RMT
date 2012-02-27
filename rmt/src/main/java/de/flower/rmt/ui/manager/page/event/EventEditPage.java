package de.flower.rmt.ui.manager.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.page.event.EventPagerPanel;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventEditPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventEditPage(IModel<Event> model) {
        setHeading("manager.event.edit.heading", null);
        addMainPanel(new EventEditPanel(model));
        addSecondaryPanel(new EventPagerPanel(model, getListModel()) {

            @Override
            protected void onClick(IModel<Event> model) {
                setResponsePage(new EventEditPage(model));
            }

        });
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll();
            }
        };
    }


    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }

}
