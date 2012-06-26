package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;
import org.apache.wicket.request.http.WebResponse;

/**
 * @author flowerrrr
 */
public class PageExpiredPage extends AbstractBaseLayoutPage {

    public static final int SC = 666;

    public PageExpiredPage() {
        setHeading("error.page.expired.heading", "error.page.expired.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new PageExpiredPanel());
    }

    @Override
    protected void setHeaders(final WebResponse response) {
        super.setHeaders(response);
        // give page a http status code
        // status code can be used by ajax-calls to determine if session has timed out (s. FullCalendarPanel).
        response.setStatus(SC);
        response.setHeader("Wicket-Status", "Page Expired");
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on error pages. confuses the user.
        return false;
    }
}
