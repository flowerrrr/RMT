package org.wicketstuff.jsr303;

import de.flower.common.annotation.Patched;
import org.apache.wicket.Component;
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

/**
 * Overriden version with custom behavior tailored to our needs.
 *
 * - can be added to component even if component is not attached to form.
 *
 * @param <T>
 */
@Patched
public class PropertyValidator<T> implements INullAcceptingValidator<T>, Serializable
{
	public static class Exclude extends Behavior
	{
		private static final long serialVersionUID = 1L;
	}

	public void validate(final IValidatable<T> validatable)
	{
        init();

		// skip, if propertyExpression is empty
		if (propertyExpression == null || propertyExpression.trim().length() == 0)
			return;

		// skip, if marked as excluded
		if (hasExclusionBehaviour())
			return;

		final T value = validatable.getValue();

		final Set<?> violations = JSR303Validation.getValidator().validateValue(beanClass,
			propertyExpression, value);
		for (final Object v : violations)
		{
			validatable.error(wrap((ConstraintViolation<?>)v).createError());
		}
	}

	private <V> ViolationErrorBuilder.Property<V> wrap(ConstraintViolation<V> violation)
	{
		return new ViolationErrorBuilder.Property<V>(violation);
	}

	private static final long serialVersionUID = 1L;

	private Class<?> beanClass;
	private String propertyExpression;

	private final Component fc;

	public PropertyValidator(final AbstractPropertyModel<?> apm, FormComponent<T> componentToApplyTo)
	{
		this.fc = componentToApplyTo;
        Assert.parameterNotNull(apm, "apm");
        this.beanClass = apm.getTarget().getClass();
        this.propertyExpression = apm.getPropertyExpression();
	}

    public PropertyValidator(Component componentToApplyTo) {
        this.fc = componentToApplyTo;
        // rest will be initialized lazily.
    }



    private void init() {
        if (this.beanClass == null) {
            AbstractPropertyModel<?> apm = (AbstractPropertyModel<?>) this.fc.getDefaultModel();
            this.beanClass = apm.getTarget().getClass();
            this.propertyExpression = apm.getPropertyExpression();
        }
    }

	private boolean hasExclusionBehaviour()
	{
		List<? extends Behavior> behaviors = fc.getBehaviors();
		for (Behavior iBehavior : behaviors)
		{
			if (iBehavior instanceof PropertyValidator.Exclude)
			{
				return true;
			}
		}

		return false;
	}

	private static transient volatile Logger _transient_logger = LoggerFactory.getLogger(PropertyValidator.class);

	public static final Logger log()
	{
		if (_transient_logger == null)
		{
			_transient_logger = LoggerFactory.getLogger(PropertyValidator.class);
		}
		return _transient_logger;
	}
}
