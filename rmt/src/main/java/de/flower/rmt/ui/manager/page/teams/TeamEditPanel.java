package de.flower.rmt.ui.manager.page.teams;

import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.EntityForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.validation.unique.Unique;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.IEntityEditPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.TeamModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;
import org.wicketstuff.jsr303.ComponentBeanValidator;
import org.wicketstuff.jsr303.ConstraintFilter;

/**
 * @author flowerrrr
 */
public class TeamEditPanel extends BasePanel implements IEntityEditPanel<Team> {

    private EntityForm<Team> form;

    @SpringBean
    private ITeamManager teamManager;

    public TeamEditPanel(String id) {
        super(id);

        form = new EntityForm<Team>("form", new TeamModel());
        add(form);

        ValidatedTextField name;
        form.add(name = new ValidatedTextField("name"));
        // add a class level validator to this property
        name.add(new ComponentBeanValidator(Unique.class, new ConstraintFilter("{de.flower.validation.constraints.unique.message.name}")));
        form.add(new ValidatedTextField("url"));

        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    teamManager.save((Team) form.getModelObject());
                    target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Team.class), AjaxEvent.EntityUpdated(Team.class)));
                    ModalWindow.closeCurrent(target);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }

    @Override
    public void init(IModel<Team> model) {
        if (model == null) {
            model = new TeamModel();
        }
        form.replaceModel(model);
    }
}
