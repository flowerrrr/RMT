package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.common.ui.inject.InjectorAwareObject;
import de.flower.rmt.model.db.type.activity.AbstractEventMessage;
import de.flower.rmt.service.IUrlProvider;
import de.flower.rmt.util.Dates;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class AbstractEventMessageRenderer extends InjectorAwareObject {

    @SpringBean(name = "wicketUrlProvider")
    protected IUrlProvider urlProvider;

    public String getEventLink(AbstractEventMessage message) {
        Object[] params = new Object[]{urlProvider.deepLinkEvent(message.getEventId()), Dates.formatDateShort(message.getEventDate())};
        String eventLink = new StringResourceModel("activity.${eventType}.tmpl", Model.of(message), params).getObject();
        return eventLink;
    }

    public String getEventArticle(AbstractEventMessage message) {
        return new StringResourceModel("activity.${eventType}.2.tmpl", Model.of(message)).getObject();
    }
}
