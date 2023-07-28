package de.flower.rmt.ui.page.error;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.LoggingUtils;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.PropertyProvider;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InternalError500Panel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(InternalError500Panel.class);

    @SpringBean
    private PropertyProvider propertyProvider;

    @SpringBean
    private SecurityService securityService;

    public InternalError500Panel(final Exception exception) {
        add(Links.contextRoot("home"));
        add(Links.mailLink("adminMail", propertyProvider.getAdminEmail(), true));
        add(new StacktracePanel(exception) {
            {
                String display = Application.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT ? "inline" : "none";
                add(AttributeModifier.replace("style", "display: " + display + ";"));
            }
            @Override
            public boolean isVisible() {
                return exception != null;
            }
        });
    }

    @Override
    protected void onBeforeRender() {
        log.warn("Server error 500 [{}]", LoggingUtils.toString(RequestCycle.get().getRequest()));
        log.warn("Userdetails: " + securityService.getCurrentUser());
        super.onBeforeRender();
    }

}
