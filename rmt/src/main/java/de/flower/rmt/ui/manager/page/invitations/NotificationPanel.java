package de.flower.rmt.ui.manager.page.invitations;

import de.flower.common.ui.js.JQuery;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.type.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.CheckBoxPanel;
import de.flower.rmt.ui.common.form.field.TextAreaPanel;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class NotificationPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    public NotificationPanel(String id, final IModel<Event> model) {
        super(id, model);

        final IModel<Notification> notificationModel = Model.of(new Notification());
        EntityForm form = new EntityForm<Notification>("form", notificationModel) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Notification> form) {
                eventManager.sendInvitationMail(model.getObject().getId(), form.getModelObject());
                notificationModel.setObject(new Notification());
                target.appendJavaScript(JQuery.scrollToTop("slow"));
            }
        };
        add(form);
        form.add(new RecipientListPanel(new PropertyModel(notificationModel, "recipients"), model));
        form.add(new TextFieldPanel("subject"));
        form.add(new TextAreaPanel("body"));
        form.add(new CheckBoxPanel("bccMySelf"));
    }
}
