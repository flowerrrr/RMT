package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListMultipleChoice;

/**
 * @author flowerrrr
 */
public class ListMultipleChoicePanel<T> extends AbstractFormFieldPanel {

    public ListMultipleChoicePanel(final String id, final FormComponent fc) {
        super(id, fc);
    }

    public static class NonValidatingListMultipleChoicePanel extends ListMultipleChoicePanel {

        public NonValidatingListMultipleChoicePanel(final String id, final ListMultipleChoice<?> listMultipleChoice) {
            super(id, listMultipleChoice);
        }

        @Override
        protected boolean isValidationEnabled() {
            return false;
        }
    }
}
