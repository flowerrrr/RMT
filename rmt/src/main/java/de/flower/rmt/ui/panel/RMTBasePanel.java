package de.flower.rmt.ui.panel;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.IViewAware;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class RMTBasePanel<T> extends BasePanel<T> {

    @SpringBean
    private ISecurityService securityService;

    public RMTBasePanel() {
        // always append a css class indicating the view
        add(AttributeModifier.append("class", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "view-" + getView().toString().toLowerCase();
            }
        }));
    }

    protected boolean isManagerView() {
        return getView() == View.MANAGER && securityService.getUser().isManager() /* redundant check, but doesn't hurt. */;
    }

    protected View getView() {
        return ((IViewAware) getPage()).getView();
    }
}
