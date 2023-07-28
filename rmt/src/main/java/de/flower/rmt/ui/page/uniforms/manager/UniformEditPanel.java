package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.service.UniformManager;
import de.flower.rmt.ui.markup.html.form.CancelableEntityForm;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class UniformEditPanel extends BasePanel {

    @SpringBean
    private UniformManager uniformManager;

    public UniformEditPanel(final IModel<Uniform> model) {

        EntityForm<Uniform> form = new CancelableEntityForm<Uniform>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Uniform> form) {
                uniformManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, Uniform.class);
                onClose(target);
            }
        };
        add(form);

        form.add(new TextFieldPanel("name"));
        form.add(new TextFieldPanel("shirt"));
        form.add(new TextFieldPanel("shorts"));
        form.add(new TextFieldPanel("socks"));
    }
}
