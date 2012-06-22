package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.app.View;

/**
 * Implemented by pages that know which view (manager or player) is active.
 * @author flowerrrr
 */
public interface IViewAware {

    View getView();

}
