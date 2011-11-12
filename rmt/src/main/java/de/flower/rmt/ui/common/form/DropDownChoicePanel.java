package de.flower.rmt.ui.common.form;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class DropDownChoicePanel<T> extends FormFieldPanel {

    public DropDownChoicePanel(String id) {
        super(id, new DropDownChoice(ID));
    }

    public DropDownChoicePanel(String id, final List<? extends T> choices) {
        super(id, new DropDownChoice(ID, choices));
    }

    public void setChoiceRenderer(IChoiceRenderer<T> iChoiceRenderer) {
        getDropDownChoice().setChoiceRenderer(iChoiceRenderer);
    }

    public void setChoices(IModel<List<T>> entityChoices) {
        getDropDownChoice().setChoices(entityChoices);
    }

    private DropDownChoice getDropDownChoice() {
        return (DropDownChoice) getFormComponent();
    }
}
