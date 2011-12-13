package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.markup.html.form.CheckBox;

/**
 * @author flowerrrr
 */
public class CheckBoxPanel extends FormFieldPanel {

    public CheckBoxPanel(String id) {
        super(id, new CheckBox(ID));
        // not much to validate, so turn off by default.
        setInstantValidationEnabled(false);
    }

}
