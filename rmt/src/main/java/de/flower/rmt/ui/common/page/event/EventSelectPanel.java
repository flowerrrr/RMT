package de.flower.rmt.ui.common.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.component.EventDropDownChoice;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class EventSelectPanel extends BasePanel<Event> {

    public EventSelectPanel(final IModel<Event> model, final IModel<List<Event>> listModel) {
        super(model);
        add(new EventDropDownChoice("selectEvent", model, listModel) {
            {
                add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        onClick((IModel<Event>) getComponent().getDefaultModel());
                    }
                });
            }
        });
    }

    @Override
    public boolean isVisible() {
        return !getModelObject().isNew();
    }

    protected abstract void onClick(IModel<Event> model);
}
