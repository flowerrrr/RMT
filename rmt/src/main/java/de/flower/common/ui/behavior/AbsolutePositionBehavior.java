package de.flower.common.ui.behavior;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;


public class AbsolutePositionBehavior extends Behavior {

    private long top;

    private long left;

    public AbsolutePositionBehavior(final long top, final long left) {
        this.top = top;
        this.left = left;
    }

    @Override
    public void bind(final Component c) {
        c.add(AttributeModifier.append("style", "position: absolute;"));
        c.add(AttributeModifier.append("style", "top: " + top + "px; left: " + left + "px;"));
    }
}