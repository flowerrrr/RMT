package de.flower.rmt.ui.app;

import de.flower.rmt.security.SecurityService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class ViewResolver {

    @Autowired
    private SecurityService securityService;

    public View getView() {
        // RMT-693
        // nav bar depends on view-page-parameter, then session-stored value, then user role
        // but: currently view-parameter is not used, only session-value.

        // by default view is set to player. will be overridden on demand.
        View view = View.PLAYER;

        if (securityService.getUser().isManager()) {
            String param = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(View.PARAM_VIEW).toString();
            View v = EnumUtils.getEnum(View.class, param);
            if (v != null) {
                // store in session
                RMTSession.get().setView(v);
                view = v;
            } else {
                view = RMTSession.get().getView();
                if (view != null) {
                    return view;
                } else {
                    view = View.MANAGER;
                }
            }
        }
        return view;
    }
}
