package de.flower.rmt.ui.common.form;

import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.util.Check;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jsr303.BeanValidator;

/**
 * Form for editing domain objects.
 * Assumes:
 * - model containing domain object is a LoadableDetachableModel. Needed to avoid
 * serializing domain objects to the page store.
 *
 * @author flowerrrr
 */
public abstract class EntityForm<T> extends Form<T> {

    public EntityForm(String id, T entity) {
        super(id, new CompoundPropertyModel<T>(entity));
        Check.notNull(entity);
        init();
    }

    public EntityForm(String id, IModel<T> model) {
        super(id, new CompoundPropertyModel<T>(model));
        Check.notNull(model);
        init();
    }

    private void init() {

        add(new FormFeedbackPanel(new ComponentFeedbackMessageFilter(this)));

        add(new MyAjaxSubmitLink("submitButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onBeforeValidation((T) form.getModelObject());
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    EntityForm.this.onSubmit(target, (Form<T>) form);
                }
            }
        });
    }

    protected void onBeforeValidation(T entity) {

    }

    protected abstract void onSubmit(AjaxRequestTarget target, Form<T> form);
}
