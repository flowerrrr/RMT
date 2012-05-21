package de.flower.rmt.ui.page.opponents.manager;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.service.IOpponentManager;
import de.flower.rmt.ui.markup.html.form.CancelableEntityForm;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.validator.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class OpponentEditPanel extends BasePanel {

    @SpringBean
    private IOpponentManager opponentManager;

    public OpponentEditPanel(final IModel<Opponent> model) {

        EntityForm<Opponent> form = new CancelableEntityForm<Opponent>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Opponent> form) {
                opponentManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, Opponent.class);
                onClose(target);
            }
        };
        add(form);

        TextFieldPanel name;
        form.add(name = new TextFieldPanel("name"));
        // add a class level validator to this property
        name.addValidator(new FormComponentBeanValidator(Opponent.Validation.INameUnique.class));
        form.add(new TextFieldPanel("url"));
    }
}
