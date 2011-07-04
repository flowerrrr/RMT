package de.flower.rmt.ui.app;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author oblume
 */
public class AbstractBasePage extends WebPage implements IAjaxIndicatorAware {

    /**
     * Display a ajax loading indicator for every ajax request.
     *
     * @return
     */
    @Override
    public String getAjaxIndicatorMarkupId() {
        return "veil";
    }

}
