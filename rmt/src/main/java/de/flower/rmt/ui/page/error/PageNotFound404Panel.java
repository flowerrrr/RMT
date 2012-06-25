package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.protocol.http.servlet.ErrorAttributes;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author flowerrrr
 */
public class PageNotFound404Panel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(PageNotFound404Panel.class);

    public PageNotFound404Panel() {
        add(Links.contextRoot("home"));
    }

    @Override
    protected void onBeforeRender() {
        ErrorAttributes errorAttributes = ErrorAttributes.of((HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest());
        if (errorAttributes != null) {
            log.info(toString(errorAttributes));
        }
        super.onBeforeRender();
    }

    private String toString(final ErrorAttributes e) {
        return "ErrorAttributes{" +
                "statusCode=" + e.getStatusCode() +
                ", message='" + e.getMessage() + '\'' +
                ", requestUri='" + e.getRequestUri() + '\'' +
                // ", servletName='" + e.getServletName() + '\'' +
                ", exceptionType=" + e.getExceptionType() +
                ", exception=" + e.getException() +
                '}';
    }
}
