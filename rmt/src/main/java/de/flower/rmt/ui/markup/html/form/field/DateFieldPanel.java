package de.flower.rmt.ui.markup.html.form.field;

import de.flower.rmt.ui.markup.html.form.DatePicker;
import de.flower.rmt.util.Dates;


public class DateFieldPanel extends AbstractFormFieldPanel {

    public DateFieldPanel(String id) {
        super(id, new DatePicker(ID, Dates.DATE_LONG));
    }

    /**
     * Since user can only input correct dates no need for instant validation.
     * Further constraints like @Past or @Future will be validated when
     * full form is submitted.
     *
     * Instant validation would also collide with datepicker.
     *
     * @return
     */
    @Override
    protected boolean isInstantValidationEnabled() {
        return false;
    }


}
