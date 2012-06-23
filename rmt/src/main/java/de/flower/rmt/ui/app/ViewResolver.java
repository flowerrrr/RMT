package de.flower.rmt.ui.app;

import de.flower.rmt.service.security.ISecurityService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class ViewResolver implements IViewResolver {

    @Autowired
    private ISecurityService securityService;

    @Override
    public View getView() {
        // RMT-693
        // nav bar depends on view-parameter, then user role

        // by default view is set to player. will be overridden on demand.
        View view = View.PLAYER;

        if (securityService.getUser().isManager()) {
            String param = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(View.PARAM_VIEW).toString();
            View v = EnumUtils.getEnum(View.class, param);
            if (v != null) {
                view = v;
            } else {
                view = View.MANAGER;
            }
        }
        return view;
    }
}
