package de.flower.rmt.ui.app;

import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Constants for both types of views that the application offers.
 *
 * @author flowerrrr
 */
public enum View {

    MANAGER,
    PLAYER;

    public static final String PARAM_VIEW = "view";

    public static PageParameters getPageParams(final View view) {
        return new PageParameters().set(PARAM_VIEW, view.toString());
    }

}
