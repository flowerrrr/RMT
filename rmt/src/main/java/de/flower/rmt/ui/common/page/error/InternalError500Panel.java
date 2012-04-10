package de.flower.rmt.ui.common.page.error;

import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
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
        add(Links.mailLink("adminMail", getAdminEmail()));
        add(new StacktracePanel(exception) {
            @Override
            public boolean isVisible() {
                return exception != null;
            }
        });
    }

    private IModel<String> getExceptionModel(final Exception exception) {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return exception.toString();
            }
        };
    }

    private String getAdminEmail() {
        return (String) templateDefaults.get("adminAddress");
    }
}
