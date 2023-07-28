package de.flower.common.ui.markup.html.form;

import org.apache.wicket.markup.html.form.DropDownChoice;

import java.util.Arrays;


public class BooleanDropDownChoice extends DropDownChoice<Boolean> {

    public BooleanDropDownChoice(final String id, final String keyPrefix) {
        this(id, keyPrefix, false);
    }

    /**
     * Reverses the list of options: false then true
     */
    public BooleanDropDownChoice(final String id, final String keyPrefix, boolean falseFirst) {
        super(id, Arrays.asList(!falseFirst, falseFirst), new BooleanChoiceRenderer(keyPrefix));
    }
}
