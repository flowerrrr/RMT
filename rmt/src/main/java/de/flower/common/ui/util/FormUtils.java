package de.flower.common.ui.util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for form handling.
 */
public final class FormUtils {

    private FormUtils() {}

    /**
     * Set focus to first form component that has a validation error.
     *
     * @param target the target
     * @param form the form
     */
    public static void focusFirstInvalidComponent(final AjaxRequestTarget target, final Form<?> form) {
        final List<FormComponent<?>> list = getInvalidComponents(form);
        target.focusComponent(list.isEmpty() ? null : list.get(0));
    }

    /**
     * Finds all form components that have validation errors.
     *
     * @param form the form
     * @return the invalid components
     */
    public static List<FormComponent<?>> getInvalidComponents(final Form<?> form) {
        final List<FormComponent<?>> list = new ArrayList<FormComponent<?>>();
        FormComponent.visitFormComponentsPostOrder(form, new IVisitor<FormComponent<?>, Void>() {

            @Override
            public void component(final FormComponent<?> component, final IVisit<Void> visit) {
                if (component.hasErrorMessage()) {
                    list.add(component);
                }
            }
        });
        return list;
    }

}
