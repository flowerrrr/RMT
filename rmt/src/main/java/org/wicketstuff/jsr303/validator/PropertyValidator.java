package org.wicketstuff.jsr303.validator;

import de.flower.common.annotation.Patched;
import de.flower.common.util.Check;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jsr303.PropertyValidationErrorBuilder;

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

    private final static Logger log = LoggerFactory.getLogger(PropertyValidator.class);

    private static final long serialVersionUID = 1L;

    private Class<T> beanClass;

    private String propertyExpression;

    private final Component fc;

    @SpringBean(name = "wicketValidator")
    private Validator validator;

    public PropertyValidator(FormComponent<T> componentToApplyTo, Class<T> beanClass, String propertyExpression) {
        this.fc = componentToApplyTo;
        this.beanClass = beanClass;
        this.propertyExpression = propertyExpression;
        Injector.get().inject(this);
    }

    public PropertyValidator(FormComponent<T> componentToApplyTo) {
        this(componentToApplyTo, null, null);
        // rest will be initialized lazily.
    }

    private void init() {
        if (this.beanClass == null) {
            AbstractPropertyModel<?> apm = getPropertyModel(fc.getDefaultModel());
            this.beanClass = (Class<T>) apm.getTarget().getClass();
            this.propertyExpression = apm.getPropertyExpression();
        }
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

        final Set<ConstraintViolation<T>> violations = validator.validateValue(beanClass, propertyExpression, value);
        for (final ConstraintViolation v : violations) {
            log.debug("Constraint violation: " + v);
            final IValidationError ve = new PropertyValidationErrorBuilder<T>(v).createError();
            validatable.error(ve);
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

    public static class Exclude extends Behavior {

        private static final long serialVersionUID = 1L;
    }
}
