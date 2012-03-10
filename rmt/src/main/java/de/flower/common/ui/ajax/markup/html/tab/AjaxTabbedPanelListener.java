package de.flower.common.ui.ajax.markup.html.tab;

import de.flower.common.ui.ajax.behavior.AjaxRequestListener;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;

/**
 * Listener that fires when a tab of an AjaxTabbedPanel is part of an ajax request target.
 * This allows other components to re-render if the visible tab changes.
 *
 * @author flowerrrr
 */
public abstract class AjaxTabbedPanelListener extends AjaxRequestListener {

    @Override
    protected void onAjaxRequest(final AjaxRequestTarget target) {
        for (Component c : target.getComponents()) {
            if (c instanceof AjaxTabbedPanel) {
                onTabRefresh(target);
            }
        }
    }

    protected abstract void onTabRefresh(AjaxRequestTarget target);
}


