package de.flower.rmt.ui.common.page;

/**
 * @author flowerrrr
 */
public interface INavigationPanelAware {

    /**
     * Page must return a string indicating which topBar menu item
     * has to be highlighted for this page.
     *
     * @return
     */
    String getActiveTopBarItem();

}
