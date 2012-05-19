package de.flower.rmt.ui.page.event.manager.notification;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.modal.ModalDialogWindow;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Collections;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IInvitationManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author flowerrrr
 */
public class RecipientListPanel extends BasePanel<List<InternetAddress>> {

    @SpringBean
    private IInvitationManager invitationManager;

    public RecipientListPanel(final IModel<List<InternetAddress>> model, final IModel<Event> eventModel) {
        super(model);

        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        add(listContainer);
        ListView<InternetAddress> list = new ListView<InternetAddress>("list", model) {

            @Override
            protected void populateItem(final ListItem<InternetAddress> item) {
                item.add(new Label("name", item.getModelObject().getPersonal()));
                item.add(new Label("email", item.getModelObject().getAddress()));
                item.add(new AjaxLink.NoIndicatingAjaxLink<InternetAddress>("deleteButton", item.getModel()) {

                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        model.getObject().remove(getModelObject());
                        onChange(target);
                        target.add(listContainer);
                    }
                });
            }
        };
        listContainer.add(list);
        add(new AjaxLink<Event>("addButton", eventModel) {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                SelectRecipientPanel content = new SelectRecipientPanel(getModel(), getInvitationListModel(getModel())) {

                    @Override
                    protected void onSubmit(final AjaxRequestTarget target, final List<InternetAddress[]> recipients) {
                        updateList(target, recipients);
                        target.add(listContainer);
                    }
                };
                ModalDialogWindow.showContent(this, content, 5);
            }
        });

        // shortcut to add all invitees without opening modal dialog
        add(new AjaxLink<Event>("addAllButton", eventModel) {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                updateList(target, convert(getInvitationListModel(getModel()).getObject()));
                target.add(listContainer);
            }
        });
        add(new AjaxLink<Event>("addUninvitedButton", eventModel) {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                updateList(target, convert(getUninvitedListModel(getModel()).getObject()));
                target.add(listContainer);
            }
        });
    }

    /**
     * @param target
     * @param recipients array of addresses (user might have two email addresses)
     */
    private void updateList(AjaxRequestTarget target, List<InternetAddress[]> recipients) {
        boolean changed = false;
        IModel<List<InternetAddress>> model = getModel();
        for (InternetAddress ia : Collections.flattenArray(recipients)) {
            if (!model.getObject().contains(ia)) {
                model.getObject().add(ia);
                changed = true;
            }
        }
        if (changed) {
            onChange(target);
        }
    }

    private List<InternetAddress[]> convert(List<Invitation> invitations) {
        return Collections.convert(invitations, new Collections.IElementConverter<Invitation, InternetAddress[]>() {
            @Override
            public InternetAddress[] convert(final Invitation element) {
                return element.getInternetAddresses();
            }
        });
    }

    protected IModel<List<Invitation>> getInvitationListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                return invitationManager.findAllForNotificationByEventSortedByName(model.getObject());
            }
        };
    }

    protected IModel<List<Invitation>> getUninvitedListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                List<Invitation> list = invitationManager.findAllForNotificationByEventSortedByName(model.getObject());
                return Lists.newArrayList(Collections2.filter(list, new Predicate<Invitation>() {
                    @Override
                    public boolean apply(@Nullable final Invitation invitation) {
                        return !invitation.isInvitationSent();
                    }
                }));
            }
        };
    }

    /**
     * Called whenever the selection changes.
     *
     * @param target
     */
    protected void onChange(AjaxRequestTarget target) {
        ;
    }
}
