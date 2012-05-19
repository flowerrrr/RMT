package org.wicketstuff.jsr303;

import de.flower.common.annotation.Patched;
import de.flower.common.util.Check;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Overriden version with custom behavior tailored to our needs.
 * <p/>
 * - can be added to component even if component is not attached to form.
 *
 * @param <T>
 */
@Patched
public class PropertyValidator<T> implements INullAcceptingValidator<T>, Serializable {

    public static class Exclude extends Behavior {

        private static final long serialVersionUID = 1L;
    }

    public void validate(final IValidatable<T> validatable) {
        init();

        // skip, if propertyExpression is empty
        if (propertyExpression == null || propertyExpression.trim().length() == 0)
            return;

        // skip, if marked as excluded
        if (hasExclusionBehaviour())
            return;

        final T value = validatable.getValue();

        final Set<?> violations = validator.validateValue(beanClass,
                propertyExpression, value);
        for (final Object v : violations) {
            validatable.error(wrap((ConstraintViolation<?>) v).createError());
        }
    }

    private <V> ViolationErrorBuilder.Property<V> wrap(ConstraintViolation<V> violation) {
        return new ViolationErrorBuilder.Property<V>(violation);
    }

    private static final long serialVersionUID = 1L;

    private Class<?> beanClass;

    private String propertyExpression;

    private final Component fc;

    private Validator validator;

    public PropertyValidator(FormComponent<T> componentToApplyTo, Class<?> beanClass, String propertyExpression) {
        this.fc = componentToApplyTo;
        this.beanClass = beanClass;
        this.propertyExpression = propertyExpression;
        validator = JSR303Validation.getInstance().getValidator();
    }

    public PropertyValidator(FormComponent<T> componentToApplyTo) {
        this(componentToApplyTo, null, null);
        // rest will be initialized lazily.
    }

    private void init() {
        if (this.beanClass == null) {
            AbstractPropertyModel<?> apm = getPropertyModel(fc.getDefaultModel());
            this.beanClass = apm.getTarget().getClass();
            this.propertyExpression = apm.getPropertyExpression();
        }
    }

    private static AbstractPropertyModel<?> getPropertyModel(final IModel<?> model) {
        Check.notNull(model);
        if (model instanceof AbstractPropertyModel) {
            return ((AbstractPropertyModel) model);
        } else if (model instanceof IChainingModel) {
            return getPropertyModel(((IChainingModel) model).getChainedModel());
        } else {
            throw new IllegalArgumentException("Could not find property model");
        }
    }

    private boolean hasExclusionBehaviour() {
        List<? extends Behavior> behaviors = fc.getBehaviors();
        for (Behavior iBehavior : behaviors) {
            if (iBehavior instanceof PropertyValidator.Exclude) {
                return true;
            }
        }

        return false;
    }
}
