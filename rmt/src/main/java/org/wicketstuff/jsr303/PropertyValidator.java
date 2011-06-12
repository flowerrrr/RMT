package org.wicketstuff.jsr303;

import de.flower.common.util.logging.Slf4jUtil;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jsr303.util.Assert;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class PropertyValidator<T> extends Behavior implements INullAcceptingValidator<T>, Serializable {
    public static class Exclude extends Behavior {
        private static final long serialVersionUID = 1L;
    }

    private static final long serialVersionUID = 1L;

    private final static Logger log = Slf4jUtil.getLogger();

    private final Class<T> beanClass;
    private final String propertyExpression;

    private final FormComponent<T> fc;

    public PropertyValidator(final AbstractPropertyModel<T> apm, FormComponent<T> componentToApplyTo) {
        this.fc = componentToApplyTo;
        Assert.parameterNotNull(apm, "apm");
        this.beanClass = (Class<T>) apm.getTarget().getClass();
        this.propertyExpression = apm.getPropertyExpression();
    }

    public void validate(final IValidatable<T> validatable) {
        // skip, if propertyExpression is empty
        if (propertyExpression == null || propertyExpression.trim().length() == 0)
            return;

        // skip, if marked as excluded
        if (hasExclusionBehaviour())
            return;

        final T value = validatable.getValue();

        log.debug("Validating [{}.{}], value [{}]", new Object[]{beanClass, propertyExpression, value});
        final Set<ConstraintViolation<T>> violations = JSR303Validation.getValidator().validateValue(beanClass,
                propertyExpression, value);
        for (ConstraintViolation<?> v : violations) {
            log.debug("Constraint violation: " + v);
            validatable.error(wrap(v).createError());
        }
    }

    private <V> ViolationErrorBuilder.Property<V> wrap(ConstraintViolation<V> violation) {
        return new ViolationErrorBuilder.Property<V>(violation);
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

    private static transient volatile Logger _transient_logger = LoggerFactory.getLogger(PropertyValidator.class);

}
