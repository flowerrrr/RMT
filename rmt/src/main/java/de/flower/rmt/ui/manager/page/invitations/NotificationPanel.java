package de.flower.rmt.ui.manager.page.invitations;

import com.google.common.annotations.VisibleForTesting;
import de.flower.common.ui.js.JQuery;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.type.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.mail.INotificationService;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.CheckBoxPanel;
import de.flower.rmt.ui.common.form.field.FormFieldPanel;
import de.flower.rmt.ui.common.form.field.TextAreaPanel;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 04.04.12) make this panel undependend from event and template service.
public class NotificationPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private INotificationService notificationService;

    public NotificationPanel(String id, final IModel<Event> model) {
        super(id, model);

        final IModel<Notification> notificationModel = Model.of(newNotification(model.getObject()));
        EntityForm form = new EntityForm<Notification>("form", notificationModel) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Notification> form) {
                eventManager.sendInvitationMail(model.getObject().getId(), form.getModelObject());
                notificationModel.setObject(newNotification(model.getObject()));
                target.appendJavaScript(JQuery.scrollToTop("slow"));
            }
        };
        add(form);

        RecipientListFormComponent fc = new RecipientListFormComponent(new PropertyModel(notificationModel, "recipients"), model);
        form.add(new FormFieldPanel("recipientList", fc) {
            @Override
            protected boolean isInstantValidationEnabled() {
                // disable needless rendering of javascript onchange handler
                return false;
            }
        });
        form.add(new TextFieldPanel("subject"));
        TextAreaPanel body = new TextAreaPanel("body");
        // disabling word wraping in firefox cannot be done by only using css. need wrap attribute on element as well
        body.getFormComponent().add(AttributeModifier.replace("wrap", "off"));
        form.add(body);
        form.add(new CheckBoxPanel("bccMySelf"));
    }

    private Notification newNotification(Event event) {
        return notificationService.newEventNotification(event, Links.deepLinkEvent(event.getId()));
    }

    /**
     * Class making the wrapped panel act like a form component. Allows to integrate panel
     * into existing form field architecture.
     */
    @VisibleForTesting
    protected static class RecipientListFormComponent extends FormComponentPanel<List<InternetAddress>> {

        public RecipientListFormComponent(final IModel<List<InternetAddress>> model, final IModel<Event> eventModel) {
            super("input", model);
            add(new RecipientListPanel(model, eventModel) {
                @Override
                protected void onChange(final AjaxRequestTarget target) {
                    // trigger instant validation, simulate AjaxFormComponentUpdatingBehavior
                    processInput();
                    target.add(this.findParent(FormFieldPanel.class));
                }
            });
        }

        @Override
        protected void convertInput() {
            setConvertedInput(getModelObject());
        }

        @Override
        public Markup getAssociatedMarkup() {
            String markup = "<wicket:panel><div wicket:id='recipientListPanel'/></wicket:panel>";
            return Markup.of(markup);
        }
    }
}
