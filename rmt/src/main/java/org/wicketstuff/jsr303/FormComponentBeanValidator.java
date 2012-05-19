package org.wicketstuff.jsr303;

import com.google.common.base.Preconditions;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Set;

/**
 * Validator that can be bound to a input field but does a bean validation.
 * Requires that the form uses a CompoundPropertyModel containing the bean
 * to validate.
 * Must be added after the component is added to a form.
 *
 * @author flowerrrr
 */
public class FormComponentBeanValidator<T> extends Behavior implements INullAcceptingValidator<String>, Serializable {

    private final static Logger log = LoggerFactory.getLogger(FormComponentBeanValidator.class);

    private Class<?>[] groups;

    private String propertyExpression;

    private Form form;

    private Validator validator;

    public FormComponentBeanValidator(Class<?>[] groups) {
        this.groups = groups;
        validator = JSR303Validation.getInstance().getValidator();
    }

    public FormComponentBeanValidator(Class<?> group) {
        this(new Class<?>[]{group});
    }

    @Override
    public void bind(Component component) {
        form = component.findParent(Form.class);
        Preconditions.checkNotNull(form);
        Preconditions.checkArgument(component instanceof FormComponent);
        if (propertyExpression == null) {
            propertyExpression = getPropertyExpression(component.getDefaultModel());
        }
    }

    private static String getPropertyExpression(final IModel<?> model) {
        Preconditions.checkNotNull(model);
        if (model instanceof AbstractPropertyModel) {
            return ((AbstractPropertyModel) model).getPropertyExpression();
        } else if (model instanceof IChainingModel) {
            return getPropertyExpression(((IChainingModel) model).getChainedModel());
        } else {
            throw new IllegalArgumentException("Could not resolve property expression for model [" + model + "]");
        }
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        T bean = (T) form.getModelObject();

        Object origValue = getProperty(bean, propertyExpression);

        setProperty(bean, propertyExpression, validatable.getValue());

        log.debug("Validating bean[{}]", bean);
        Set<ConstraintViolation<T>> violations = validator.validate(bean, groups);

        for (ConstraintViolation<T> v : violations) {
            log.debug("Constraint violation: " + v);
            validatable.error(new ViolationErrorBuilder.Property<T>(v).createError());
        }
        setProperty(bean, propertyExpression, origValue);
    }

    private void setProperty(final T bean, final String propertyExpression, final Object value) {
        PropertyResolver.setValue(propertyExpression, bean, value, null);
    }

    private Object getProperty(final T bean, final String propertyExpression) {
        return PropertyResolver.getValue(propertyExpression, bean);
    }
}
