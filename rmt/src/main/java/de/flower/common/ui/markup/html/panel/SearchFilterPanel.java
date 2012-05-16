package de.flower.common.ui.markup.html.panel;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Resource;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Input box with associated search-filter.js to filter elements in html pages.
 *
 * @author flowerrrr
 */
public class SearchFilterPanel extends BasePanel {

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavaScriptReference(Resource.searchFilterJsUrl);

    }
}
