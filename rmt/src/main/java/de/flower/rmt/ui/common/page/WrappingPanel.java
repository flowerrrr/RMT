package de.flower.rmt.ui.common.page;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.Markup;

import java.util.Iterator;

/**
 * Allows to add an arbitrary list of components to a container panel
 * @author flowerrrr
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
            divs += "<div wicket:id=\"" + iterator.next().getId() + "\" />\n";
        }
        return Markup.of("<wicket:panel>" + divs + "</wicket:panel>");
    }
}
