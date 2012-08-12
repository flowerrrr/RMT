package de.flower.rmt.ui.markup.html.form;

import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.util.Check;
import de.flower.rmt.ui.markup.html.panel.FormFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * Form for editing domain objects.
 * Assumes:
 * - model containing domain object is a LoadableDetachableModel. Needed to avoid
 * serializing domain objects to the page store.
 *
 * @author flowerrrr
 */
public abstract class EntityForm<T> extends Form<T> {

    private AjaxSubmitLink ajaxSubmitLink;

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

        add(new FormFeedbackPanel(this) {
            @Override
            public boolean isShowSuccessFeedbackPanel() {
                return EntityForm.this.isShowSuccessFeedbackPanel();
            }
        });

        ajaxSubmitLink = new AjaxSubmitLink("submitButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onBeforeValidation((T) form.getModelObject());
                if (!new org.wicketstuff.jsr303.validator.BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                    target.add(EntityForm.this);
                } else {
                    EntityForm.this.onSubmit(target, (Form<T>) form);
                    target.add(EntityForm.this);
                }
            }
        };
        add(ajaxSubmitLink);
    }

    /**
     * Override this method to hide feedback panel after submit.
     *
     * @return
     */
    protected boolean isShowSuccessFeedbackPanel() {
        return true;
    }

    protected void onBeforeValidation(T entity) {

    }

    public AjaxSubmitLink getAjaxSubmitLink() {
        return ajaxSubmitLink;
    }

    protected abstract void onSubmit(AjaxRequestTarget target, Form<T> form);
}
