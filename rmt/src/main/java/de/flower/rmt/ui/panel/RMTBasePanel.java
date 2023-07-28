package de.flower.rmt.ui.panel;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.app.ViewResolver;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class RMTBasePanel<T> extends BasePanel<T> {

    @SpringBean
    protected SecurityService securityService;

    @SpringBean
    private ViewResolver viewResolver;

    public RMTBasePanel() {
        this(null, null);
    }

    public RMTBasePanel(String id) {
        this(id, null);
    }

    public RMTBasePanel(final IModel<T> model) {
        this(null, model);
    }

    public RMTBasePanel(final String id, final IModel<T> model) {
        super(id, model);
        // always append a css class indicating the view
        add(AttributeModifier.append("class", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return (getView() == null) ? "" : "view-" + getView().toString().toLowerCase();
            }
        }));
    }

    public boolean isManagerView() {
        return getView() == View.MANAGER && securityService.getUser().isManager() /* redundant check, but doesn't hurt. */;
    }

    protected View getView() {
        return viewResolver.getView();
    }

    protected User getUser() {
        return securityService.getUser();
    }
}
