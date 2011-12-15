package de.flower.common.ui.form;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class BooleanChoiceRenderer implements IChoiceRenderer<Boolean> {

    private String sTrue;

    private String sFalse;

    public BooleanChoiceRenderer(final String sTrue, final String sFalse) {
        this.sTrue = sTrue;
        this.sFalse = sFalse;
    }

    public BooleanChoiceRenderer(final String keyPrefix) {
        this.sTrue = new ResourceModel(keyPrefix + ".true").getObject();
        this.sFalse = new ResourceModel(keyPrefix + ".false").getObject();
    }

    @Override
    public Object getDisplayValue(final Boolean object) {
        return object ? sTrue : sFalse;
    }

    @Override
    public String getIdValue(final Boolean object, final int index) {
        return object.toString();
    }
}
