package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Uniform;
import de.flower.rmt.service.IUniformManager;
import de.flower.rmt.ui.model.UniformModel;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class UniformListPanel extends BasePanel<Team> {

    @SpringBean
    private IUniformManager uniformManager;

    public UniformListPanel(final IModel<Team> model) {
        super(model);

        final IModel<List<Uniform>> listModel = getListModel(model);

        WebMarkupContainer uniformListContainer = new WebMarkupContainer("listContainer");
        add(uniformListContainer);
        uniformListContainer.add(new WebMarkupContainer("noUniform") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        uniformListContainer.add(new EntityListView<Uniform>("uniformList", listModel) {

            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<Uniform> item) {
                final Uniform uniform = item.getModelObject();
                item.setModel(new CompoundPropertyModel<Uniform>(item.getModel()));
                Link editLink = createEditLink("editLink", item, model);
                editLink.add(new Label("name", uniform.getName()));
                item.add(editLink);
                item.add(new Label("shirt"));
                item.add(new Label("shorts"));
                item.add(new Label("socks"));
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                item.add(menuPanel);
                menuPanel.addLink(createEditLink("link", item, model), "button.edit");
                menuPanel.addLink(new AjaxLinkWithConfirmation("link", new ResourceModel("manager.uniforms.delete.confirm")) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        uniformManager.delete(item.getModelObject().getId());
                        AjaxEventSender.entityEvent(this, Uniform.class);
                    }
                }, "button.delete");
            }
        });
        uniformListContainer.add(new AjaxEventListener(Uniform.class));
    }

    private IModel<List<Uniform>> getListModel(final IModel<Team> model) {
        return new LoadableDetachableModel<List<Uniform>>() {
            @Override
            protected List<Uniform> load() {
                return uniformManager.findAllByTeam(model.getObject());
            }
        };
    }

    private Link createEditLink(String id, final ListItem<Uniform> item, final IModel<Team> teamModel) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new UniformEditPage(new UniformModel(item.getModel().getObject()), teamModel));
            }
        };
    }
}
