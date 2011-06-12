package de.flower.rmt.ui.common.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * @author oblume
 */
public class AjaxRespondListener implements AjaxRequestTarget.ITargetRespondListener {

    private AjaxEvent[] events;

    public AjaxRespondListener(AjaxEvent... events) {
        this.events = events;
    }

    @Override
    public void onTargetRespond(final AjaxRequestTarget target) {
        Page page = target.getPage();

        IVisitor<Component, Void> visitor = new IVisitor<Component, Void>() {

            @Override
            public void component(Component component, IVisit<Void> objectIVisit) {
                if (component instanceof IAjaxUpdateEventListener) {
                    IAjaxUpdateEventListener listener = (IAjaxUpdateEventListener) component;
                    listener.update(target, events);
                }
            }
        };
        page.visitChildren(visitor);
        // and don't forget to visit page itself.
        visitor.component(page, null);
    }
}
