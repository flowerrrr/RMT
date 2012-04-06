package de.flower.rmt.ui.manager.page.teams;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.form.CancelableEntityForm;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
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

        EntityForm<Team> form = new CancelableEntityForm<Team>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Team> form) {
                teamManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, Team.class);
                onClose(target);
            }

        };
        add(form);

        TextFieldPanel name;
        form.add(name = new TextFieldPanel("name"));
        // add a class level validator to this property
        name.addValidator(new FormComponentBeanValidator(Team.Validation.INameUnique.class));
        form.add(new TextFieldPanel("url"));
    }

}
