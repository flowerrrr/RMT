package de.flower.rmt.ui.app;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author oblume
 */
@Deprecated // might be obsolete
public class AbstractBasePage extends WebPage implements IAjaxIndicatorAware {

    @Override
    public String getAjaxIndicatorMarkupId() {
        return "veil";
    }

}
