package de.flower.rmt.ui.page.event.manager.edit;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class CopyEventPanel extends BasePanel<Event> {

    @SpringBean
    private EventManager eventManager;

    public CopyEventPanel(final IModel<Event> model) {
        super(model);

        add(new AjaxLink("copyButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                Event event = eventManager.copyOf(model.getObject());
                setResponsePage(new EventPage(new EventModel(event)));
            }
        });
    }

    @Override
    public boolean isVisible() {
        return !getModelObject().isNew();
    }
}
