package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Map;

/**
 * @author flowerrrr
 */
public class InternalError500Panel extends BasePanel {

    @SpringBean(name = "templateDefaults")
    private Map<?, ?> templateDefaults;

    public InternalError500Panel(final Exception exception) {
        add(Links.homePage("home"));
        add(Links.adminMailLink("adminMail", true));
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

    private String getAdminEmail() {
        return (String) templateDefaults.get("adminAddress");
    }
}
