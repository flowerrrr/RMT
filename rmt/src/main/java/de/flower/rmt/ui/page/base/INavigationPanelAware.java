package de.flower.rmt.ui.page.base;


public interface INavigationPanelAware {

    /**
     * Page must return a string indicating which navigationPanel (topBar) menu item
     * has to be highlighted for this page.
     *
     * @return
     */
    String getActiveTopBarItem();

}
