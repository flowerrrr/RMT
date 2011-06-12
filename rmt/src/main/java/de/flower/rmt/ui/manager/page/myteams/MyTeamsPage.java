package de.flower.rmt.ui.manager.page.myteams;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.service.IMyTeamManager;
import de.flower.rmt.ui.common.ajax.AjaxEvent;
import de.flower.rmt.ui.common.ajax.AjaxRespondListener;
import de.flower.rmt.ui.common.page.ModalDialogWindow;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author oblume
 */
public class MyTeamsPage extends ManagerBasePage {

    @SpringBean
    private IMyTeamManager myTeamManager;

    public MyTeamsPage() {

        final ModalDialogWindow modal = new ModalDialogWindow("teamDialog");
        final MyTeamEditPanel teamEditPanel = new MyTeamEditPanel(modal.getContentId());
        modal.setContent(teamEditPanel);
        add(modal);

        add(new AjaxLink("newButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show modal dialog with team edit form.
                teamEditPanel.init(null);
                modal.show(target);
            }
        });

        WebMarkupContainer teamListContainer = new WebMarkupContainer("teamListContainer");
        add(teamListContainer);
        teamListContainer.add(new ListView<MyTeamBE>("teamList", getTeamListModel()) {

            @Override
            protected void populateItem(final ListItem<MyTeamBE> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new AjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        teamEditPanel.init(item.getModel());
                        modal.show(target);
                    }
                });
                item.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.myteams.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        myTeamManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.MYTEAM_DELETED));
                    }
                });
            }
        });
        register(teamListContainer /* listview cannot be updated */, AjaxEvent.MYTEAM_CREATED, AjaxEvent.MYTEAM_CHANGED, AjaxEvent.MYTEAM_DELETED);

    }


    private IModel<List<MyTeamBE>> getTeamListModel() {
        return new LoadableDetachableModel<List<MyTeamBE>>() {
            @Override
            protected List<MyTeamBE> load() {
                return myTeamManager.findAll();
            }
        };
    }
}
