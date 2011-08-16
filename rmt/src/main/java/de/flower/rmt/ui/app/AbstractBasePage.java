package de.flower.rmt.ui.app;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

/**
 * @author oblume
 */
public class AbstractBasePage extends WebPage implements IAjaxIndicatorAware {

    public AbstractBasePage() {
        super();
    }

    public AbstractBasePage(IModel<?> model) {
        super(model);
    }

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
