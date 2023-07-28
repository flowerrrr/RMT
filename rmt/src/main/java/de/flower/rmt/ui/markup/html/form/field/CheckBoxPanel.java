package de.flower.rmt.ui.markup.html.form.field;

import de.flower.common.util.Check;
import org.apache.wicket.markup.html.form.CheckBox;


public class CheckBoxPanel extends AbstractFormFieldPanel {

    public CheckBoxPanel(String id) {
        this(id, new CheckBox(ID));
    }

    public CheckBoxPanel(final String id, final CheckBox checkBox) {
        super(id, checkBox);
        Check.isEqual(checkBox.getId(), AbstractFormFieldPanel.ID);
    }

    @Override
    protected boolean isValidationEnabled() {
        return false;
    }
}
