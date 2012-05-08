package de.flower.rmt.ui.page.event;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.markup.html.form.EventDropDownChoice;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @author flowerrrr
 */
@Deprecated
public abstract class EventSelectPanel extends BasePanel<Event> {

    private EventDropDownChoice eventDropDownChoice;

    public EventSelectPanel(final IModel<Event> model) {
        this(model, new Model());
    }

    private EventSelectPanel(final IModel<Event> model, final IModel<List<Event>> listModel) {
        super(model);
        eventDropDownChoice = new EventDropDownChoice("selectEvent", model, listModel) {
            {
                add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        onClick((IModel<Event>) getComponent().getDefaultModel());
                    }
                });
            }
        };
        add(eventDropDownChoice);
    }

    public void setListModel(IModel<List<Event>> listModel) {
        eventDropDownChoice.setChoices(listModel);
    }

    @Override
    public boolean isVisible() {
        return !getModelObject().isNew();
    }

    protected abstract void onClick(IModel<Event> model);
}
