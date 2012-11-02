package de.flower.rmt.ui.page.base;

import org.apache.wicket.Component;

/**
 * Introduced to avoid cyclic dependency from page.base package to all other page packages.
 *
 * @author flowerrrr
 */
public interface IPanelProvider {

    Component getNavigationPanel(INavigationPanelAware page);
}
