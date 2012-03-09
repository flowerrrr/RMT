package de.flower.rmt.ui.manager.page.opponents;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Opponent;
import de.flower.rmt.service.IOpponentManager;
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
public class OpponentEditPanel extends BasePanel {

    @SpringBean
    private IOpponentManager opponentManager;

    public OpponentEditPanel(final IModel<Opponent> model) {

        EntityForm<Opponent> form = new CancelableEntityForm<Opponent>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Opponent> form) {
                opponentManager.save(form.getModelObject());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Opponent.class), AjaxEvent.EntityUpdated(Opponent.class)));
                onClose();
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
