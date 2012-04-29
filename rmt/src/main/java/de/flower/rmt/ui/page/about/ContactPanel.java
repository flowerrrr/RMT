package de.flower.rmt.ui.page.about;

import de.flower.rmt.ui.app.IPropertyProvider;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ContactPanel extends BasePanel {

    @SpringBean
    private IPropertyProvider propertyProvider;

    public ContactPanel() {
        add(Links.mailLink("adminMailLink", propertyProvider.getAdminEmail(), true));
    }

}
