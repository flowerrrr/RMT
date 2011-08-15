package de.flower.rmt.ui.common.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author oblume
 */
public class BasePanel extends Panel {

    public BasePanel(String id) {
        super(id);
    }

    public BasePanel(String id, IModel<?> model) {
        super(id, model);
    }
}
