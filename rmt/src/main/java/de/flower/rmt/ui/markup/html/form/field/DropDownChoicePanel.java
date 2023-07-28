package de.flower.rmt.ui.markup.html.form.field;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;


public class DropDownChoicePanel<T> extends AbstractFormFieldPanel {

    public DropDownChoicePanel(String id) {
        super(id, new DropDownChoice<T>(ID));
    }

    public DropDownChoicePanel(String id, final List<? extends T> choices) {
        super(id, new DropDownChoice<T>(ID, choices));
    }

    public DropDownChoicePanel(final String id, final DropDownChoice<?> dropDownChoice) {
        super(id, dropDownChoice);
    }

    public void setChoiceRenderer(IChoiceRenderer<T> iChoiceRenderer) {
        getDropDownChoice().setChoiceRenderer(iChoiceRenderer);
    }

    public void setChoices(IModel<List<T>> entityChoices) {
        getDropDownChoice().setChoices(entityChoices);
    }

    private DropDownChoice<T> getDropDownChoice() {
        return (DropDownChoice<T>) getFormComponent();
    }

    public static class NonValidatingDropDownChoicePanel extends DropDownChoicePanel {

        public NonValidatingDropDownChoicePanel(final String id, final DropDownChoice<?> dropDownChoice) {
            super(id, dropDownChoice);
        }

        @Override
        protected boolean isValidationEnabled() {
            return false;
        }
    }
}
