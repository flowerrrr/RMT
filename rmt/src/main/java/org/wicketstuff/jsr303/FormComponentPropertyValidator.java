package org.wicketstuff.jsr303;

import de.flower.common.util.Check;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

/**
 * Validator that can be bound to a input field and does a property validation.
 * Requires that the form uses a CompoundPropertyModel containing the bean
 * to validate.
 * Must be added after the component is added to a form.
 *
 * @author flowerrrr
 */
public class FormComponentPropertyValidator<T> extends Behavior implements INullAcceptingValidator<String>, Serializable {

    private final static Logger log = LoggerFactory.getLogger(FormComponentPropertyValidator.class);

    private Class<?>[] groups;

    private String propertyExpression;

    private Form form;

    public FormComponentPropertyValidator(Class<?>[] groups) {
        this.groups = groups;
    }

    public FormComponentPropertyValidator(Class<?> group) {
        this(new Class<?>[]{group});
    }

    @Override
    public void bind(Component component) {
        form = component.findParent(Form.class);
        Check.notNull(form);
        Check.isTrue(component instanceof FormComponent);
        if (propertyExpression == null) {
            propertyExpression = getPropertyExpression((FormComponent) component);
        }
    }

    private String getPropertyExpression(final FormComponent component) {
        AbstractPropertyModel<?> model = (AbstractPropertyModel<?>) component.getModel();
        return model.getPropertyExpression();
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        T bean = (T) form.getModelObject();

        Object origValue = getProperty(bean, propertyExpression);

        setProperty(bean, propertyExpression, validatable.getValue());

        log.debug("Validating bean[{}]", bean);
        Set<ConstraintViolation<T>> violations = JSR303Validation.getValidator().validateProperty(bean, propertyExpression, groups);

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
