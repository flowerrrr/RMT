package de.flower.rmt.ui.manager.page.event.notification;

import de.flower.common.ui.modal.ModalPanel;
import de.flower.common.ui.model.AbstractWrappingModel;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class SelectRecipientPanel extends ModalPanel {

    private List<InternetAddress[]> recipients = new ArrayList<InternetAddress[]>();

    public SelectRecipientPanel(final IModel<Event> model, IModel<List<Invitation>> invitationListModel) {
        super();

        setHeading("manager.notification.select.recipients.heading");
        setSubmitLabel("button.add");

        final Form form = new Form("form");
        addForm(form);

        // list of invitees

        final CheckGroup<InternetAddress[]> group = new CheckGroup<InternetAddress[]>("group", recipients);
        form.add(group);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        group.add(listContainer);
        ListView invitationList = new ListView<Invitation>("list", invitationListModel) {

            @Override
            protected void populateItem(ListItem<Invitation> item) {
                Invitation invitation = item.getModelObject();
                item.add(new Check("checkbox", new CheckModel(item.getModel())));
                item.add(new Label("name", invitation.getName()));
            }
        };
        listContainer.add(invitationList);
    }

    @Override
    protected boolean onSubmit(final AjaxRequestTarget target, final Form form) {
        onSubmit(target, recipients);
        return true;
    }

    protected abstract void onSubmit(final AjaxRequestTarget target, final List<InternetAddress[]> recipients);

    private static class CheckModel extends AbstractWrappingModel<InternetAddress[], Invitation> {

        public CheckModel(final IModel<Invitation> wrappedModel) {
            super(wrappedModel);
        }

        @Override
        public InternetAddress[] getObject() {
            return getWrappedModelObject().getInternetAddresses();
        }

        @Override
        public void setObject(final InternetAddress[] object) {
            throw new UnsupportedOperationException();
        }
    }
}
