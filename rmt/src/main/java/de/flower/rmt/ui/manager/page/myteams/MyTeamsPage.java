package de.flower.rmt.ui.manager.page.myteams;

import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.service.IMyTeamManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author oblume
 */
public class MyTeamsPage extends ManagerBasePage {

    @SpringBean
    private IMyTeamManager myTeamManager;

    public MyTeamsPage() {

        add(new ListView<MyTeamBE>("teamList", getTeamListModel()) {

            @Override
            protected void populateItem(ListItem<MyTeamBE> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Link("editButton") {

                    @Override
                    public void onClick() {
                        throw new UnsupportedOperationException("Feature not implemented!");
                    }
                });
                item.add(new Link("deleteButton") {

                    @Override
                    public void onClick() {
                        throw new UnsupportedOperationException("Feature not implemented!");
                    }
                });
            }
        });
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
