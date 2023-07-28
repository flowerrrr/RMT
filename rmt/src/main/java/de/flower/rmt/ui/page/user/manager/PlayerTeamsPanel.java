package de.flower.rmt.ui.page.user.manager;

import de.flower.common.ui.markup.html.form.BooleanDropDownChoice;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.Player_;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.PlayerManager;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.List;

/**
 * @author flowerrrr
 */
public class PlayerTeamsPanel extends BasePanel<User> {

    @SpringBean
    private PlayerManager playerManager;

    public PlayerTeamsPanel(String id, IModel<User> model) {
        super(id, model);

        final IModel<? extends List<? extends Player>> listModel = getListModel(model);
        final WebMarkupContainer noTeam = new WebMarkupContainer("noTeam") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        };
        add(noTeam);

        ListView<Player> list = new EntityListView<Player>("list", listModel) {

            @Override
            public boolean isVisible() {
                return !noTeam.isVisible();
            }

            @Override
            protected void populateItem(final ListItem<Player> item) {
                Player player = item.getModelObject();
                item.add(new Label("name", player.getTeam().getName()));
                item.add(createForm(item.getModel()));
            }
        };
        add(list);
    }

    private Form<Player> createForm(IModel<Player> model) {
        final Form<Player> form = new Form<Player>("form", new CompoundPropertyModel<Player>(model));

        DropDownChoice<Boolean> dropDownChoice = new BooleanDropDownChoice("input", "choice.player.notification");
        dropDownChoice.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                playerManager.save(form.getModelObject());
            }
        });
        form.add(new DropDownChoicePanel.NonValidatingDropDownChoicePanel("notification", dropDownChoice));

        /*
        dropDownChoice = new BooleanDropDownChoice("input", "choice.player.response.optional", true);
        dropDownChoice.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                playerManager.save(form.getModelObject());
            }
        });
        form.add(new DropDownChoicePanel.NonValidatingDropDownChoicePanel("optional", dropDownChoice));


        dropDownChoice = new BooleanDropDownChoice("input", "choice.player.retired", true);
        dropDownChoice.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                playerManager.save(form.getModelObject());
            }
        });
        form.add(new DropDownChoicePanel.NonValidatingDropDownChoicePanel("retired", dropDownChoice));
        */

        return form;
    }

    private IModel<? extends List<? extends Player>> getListModel(final IModel<User> model) {
        return new LoadableDetachableModel<List<? extends Player>>() {
            @Override
            protected List<? extends Player> load() {
                if (model.getObject().isNew()) {
                    return Collections.emptyList();
                } else {
                    List<Player> allByUser = playerManager.findAllByUser(model.getObject(), Player_.team);
                    return playerManager.sortByTeam(allByUser);
                }
            }
        };
    }
}
