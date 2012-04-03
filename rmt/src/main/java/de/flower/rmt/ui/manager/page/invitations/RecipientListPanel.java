package de.flower.rmt.ui.manager.page.invitations;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.modal.ModalDialogWindow;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author flowerrrr
 */
public class RecipientListPanel extends BasePanel {

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
                        target.add(listContainer);
                    }
                });
            }
        };
        listContainer.add(list);
        add(new AjaxLink<Event>("addButton", eventModel) {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                SelectRecipientPanel content = new SelectRecipientPanel(getModel()) {

                    @Override
                    protected void onSubmit(final AjaxRequestTarget target, final List<InternetAddress> recipients) {
                        System.out.println(recipients.toString());
                        for (InternetAddress ia : recipients) {
                            if (!model.getObject().contains(ia)) {
                                model.getObject().add(ia);
                            }
                        }
                        target.add(listContainer);
                    }
                };
                ModalDialogWindow.showContent(this, content, 5);
            }
        });
    }
}
