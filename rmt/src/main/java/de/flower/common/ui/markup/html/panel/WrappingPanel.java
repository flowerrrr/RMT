package de.flower.common.ui.markup.html.panel;

import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.Markup;

import java.util.Iterator;

/**
 * Allows to add an arbitrary list of components to a container panel
 */
public class WrappingPanel extends BasePanel {

    public WrappingPanel(String id, final Component ... components) {
        super(id);
        for (Component component : components) {
            add(component);
        }
    }

    @Override
    public Markup getAssociatedMarkup() {
        String divs = "";
        final Iterator<Component> iterator = this.iterator();
        while (iterator.hasNext()) {
            String id = iterator.next().getId();
            divs += "<div wicket:id=\"" + id + "\" />\n";
        }
        return Markup.of("<wicket:panel>" + divs + "</wicket:panel>");
    }
}
