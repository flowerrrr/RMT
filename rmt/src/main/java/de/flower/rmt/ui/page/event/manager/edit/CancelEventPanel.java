package de.flower.rmt.ui.page.event.manager.edit;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.EventManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class CancelEventPanel extends BasePanel<Event> {

    @SpringBean
    private EventManager eventManager;

    public CancelEventPanel(final IModel<Event> model) {
        super(model);

        add(new AjaxLinkWithConfirmation("cancelButton", new ResourceModel("manager.event.cancel.confirm")) {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                eventManager.cancelEvent(model.getObject().getId());
                model.detach();
                AjaxEventSender.entityEvent(this, Event.class);
            }
        });
    }

    @Override
    public boolean isVisible() {
        return !getModelObject().isNew() && !getModelObject().isCanceled();
    }
}
