package de.flower.rmt.ui.manager.page.invitations;

import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.modal.ModalPanel;
import de.flower.common.ui.model.AbstractWrappingModel;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class SelectRecipientPanel extends ModalPanel {

    @SpringBean
    private IInvitationManager invitationManager;

    private List<InternetAddress> recipients = new ArrayList<InternetAddress>();

    public SelectRecipientPanel(final IModel<Event> model) {
        super(model);

        setHeading("manager.notification.select.recipients.heading");

        final Form form = new Form("form");
        add(form);

        // list of invitees

        final CheckGroup<InternetAddress> group = new CheckGroup<InternetAddress>("group", recipients);
        form.add(group);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        group.add(listContainer);
        ListView invitationList = new ListView<Invitation>("list", getListModel(model)) {

            @Override
            protected void populateItem(ListItem<Invitation> item) {
                Invitation invitation = item.getModelObject();
                item.add(new Check("checkbox", new CheckModel(item.getModel())));
                item.add(new Label("name", invitation.getName()));
            }
        };
        listContainer.add(invitationList);

        add(new AjaxSubmitLink("submitButton", form) {
            {
                add(new Label("submitLabel", new ResourceModel("button.add")));
            }
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                SelectRecipientPanel.this.onSubmit(target, recipients);
                close(target);
            }
        });

        add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Invitation.class)));
    }

    protected abstract void onSubmit(final AjaxRequestTarget target, final List<InternetAddress> recipients);

    /**
     * Selects all unassigned players of a club.
     *
     * @return
     */
    protected IModel<List<Invitation>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                return invitationManager.findAllByEvent(model.getObject(), Invitation_.user);
            }
        };
    }

    private static class CheckModel extends AbstractWrappingModel<InternetAddress, Invitation> {

        public CheckModel(final IModel<Invitation> wrappedModel) {
            super(wrappedModel);
        }

        @Override
        public InternetAddress getObject() {
            try {
                return new InternetAddress(getWrappedModelObject().getEmail(), getWrappedModelObject().getName());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setObject(final InternetAddress object) {
            throw new UnsupportedOperationException();
        }
    }
}
