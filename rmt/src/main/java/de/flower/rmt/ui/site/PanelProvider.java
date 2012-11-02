package de.flower.rmt.ui.site;

import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.base.IPanelProvider;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class PanelProvider implements IPanelProvider {

    @Override
    public org.apache.wicket.Component getNavigationPanel(final INavigationPanelAware page) {
        return new NavigationPanel(page);
    }
}
