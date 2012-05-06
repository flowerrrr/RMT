package de.flower.rmt.ui.panel;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Check;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class DropDownMenuPanel extends BasePanel {

    public final static String LINK_ID = "link";

    private List<AbstractLink> links = new ArrayList<AbstractLink>();

    public DropDownMenuPanel() {
        ListView<AbstractLink> list = new ListView<AbstractLink>("linkList", links) {
            @Override
            public boolean isVisible() {
                return !links.isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<AbstractLink> item) {
                item.add(item.getModelObject());
            }
        };
        add(list);
    }

    /**
     * Adds a link that has already set its label.
     * @param link
     */
    public void addLink(final AbstractLink link) {
        Check.isEqual(link.getId(), LINK_ID);
        links.add(link);
    }

    /**
     * Adds a link with a label.
     *
     * @param link
     * @param labelKey
     */
    public void addLink(final AbstractLink link, final String labelKey) {
        Check.isEqual(link.getId(), LINK_ID);
        Label label = new Label("label", new ResourceModel(labelKey));
        link.add(label);
        links.add(link);
    }
}
