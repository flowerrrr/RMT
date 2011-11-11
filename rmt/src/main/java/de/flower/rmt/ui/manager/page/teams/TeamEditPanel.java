package de.flower.rmt.ui.manager.page.teams;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.CancelableEntityForm;
import de.flower.common.ui.form.EntityForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.validation.groups.INameUnique;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class TeamEditPanel extends BasePanel {

    @SpringBean
    private ITeamManager teamManager;

    public TeamEditPanel(final IModel<Team> model) {
        super();

        EntityForm<Team> form = new CancelableEntityForm<Team>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Team> form) {
                teamManager.save(form.getModelObject());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Team.class), AjaxEvent.EntityUpdated(Team.class)));
                onClose(target);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target) {
                onClose(target);
            }
        };
        add(form);

        ValidatedTextField name;
        form.add(name = new ValidatedTextField("name"));
        // add a class level validator to this property
        name.add(new FormComponentBeanValidator(INameUnique.class));
        form.add(new ValidatedTextField("url"));
    }

}
