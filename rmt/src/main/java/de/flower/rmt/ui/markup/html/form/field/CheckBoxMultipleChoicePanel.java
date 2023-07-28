package de.flower.rmt.ui.markup.html.form.field;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListMultipleChoice;


public class CheckBoxMultipleChoicePanel<T> extends AbstractFormFieldPanel {

    public CheckBoxMultipleChoicePanel(final String id, final FormComponent fc) {
        super(id, fc);
    }

    public static class NonValidatingListMultipleChoicePanel extends CheckBoxMultipleChoicePanel {

        public NonValidatingListMultipleChoicePanel(final String id, final ListMultipleChoice<?> listMultipleChoice) {
            super(id, listMultipleChoice);
        }

        @Override
        protected boolean isValidationEnabled() {
            return false;
        }
    }
}
