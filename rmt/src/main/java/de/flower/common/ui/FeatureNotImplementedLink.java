package de.flower.common.ui;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class FeatureNotImplementedLink extends Link {

    public FeatureNotImplementedLink(String id, String message) {
        super(id);
        add(new AttributeModifier("onclick", "alert('Upps. This feature is not implemented yet.\\n\\n" + message + "'); return false;"));
    }


    @Override
    public void onClick() {
        // will never be called
        throw new IllegalStateException();
    }
}
