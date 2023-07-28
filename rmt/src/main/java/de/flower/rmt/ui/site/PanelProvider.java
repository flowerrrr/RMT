package de.flower.rmt.ui.site;

import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.springframework.stereotype.Component;


@Component
public class PanelProvider {

    public org.apache.wicket.Component getNavigationPanel(final INavigationPanelAware page) {
        return new NavigationPanel(page);
    }
}
