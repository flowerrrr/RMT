package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.app.IPropertyProvider;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InternalError500Panel extends BasePanel {

    @SpringBean
    private IPropertyProvider propertyProvider;

    public InternalError500Panel(final Exception exception) {
        add(Links.homePage("home"));
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
}
